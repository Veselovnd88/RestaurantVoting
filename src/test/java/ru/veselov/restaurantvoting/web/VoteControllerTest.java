package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.exception.RestaurantNotFoundException;
import ru.veselov.restaurantvoting.exception.VotingTimeLimitExceedsException;
import ru.veselov.restaurantvoting.service.menu.MenuService;
import ru.veselov.restaurantvoting.service.vote.VoteService;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.util.SecurityUtils;
import ru.veselov.restaurantvoting.util.TestUtils;
import ru.veselov.restaurantvoting.util.UserTestData;
import ru.veselov.restaurantvoting.util.VoteTestData;

import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

class VoteControllerTest extends AbstractRestControllerTest {

    @Autowired
    VoteService voteService;

    @Autowired
    MenuService menuService;

    @Value("${vote.limit-time}")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime limitTime;

    @MockBean
    Clock clock;

    @BeforeEach
    void setUp() {
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
    }

    @Test
    @SneakyThrows
    void vote_AllOk_ReturnDtoWithLocation() {
        configureClockMockForTimeNotExceeds();

        mockMvc.perform(MockMvcUtils.vote(RestaurantTestData.BURGER_ID)
                        .with(SecurityUtils.userHttpBasic(UserTestData.user3)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(VoteTestData.VOTE_DTO_MATCHER.contentJson(VoteTestData.user3VoteBurgerDto));
    }

    @Test
    @SneakyThrows
    void vote_VoteForThisDayAlreadyExists_ReturnConflictError() {
        configureClockMockForTimeNotExceeds();

        ResultActions resultActions = mockMvc.perform(MockMvcUtils.vote(RestaurantTestData.BURGER_ID)
                .with(SecurityUtils.userHttpBasic(UserTestData.user2)));

        ResultActionErrorsUtil.checkConflictError(resultActions, GlobalExceptionHandler.VOTE_FOR_TODAY_EXISTS,
                VoteController.REST_URL);
    }

    @Test
    @SneakyThrows
    void vote_RestaurantFoundForFirstVote_ReturnError() {
        configureClockMockForTimeNotExceeds();

        ResultActions resultActions = mockMvc.perform(MockMvcUtils.vote(TestUtils.NOT_FOUND)
                        .with(SecurityUtils.userHttpBasic(UserTestData.user3)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        ResultActionErrorsUtil.checkNotFoundFields(resultActions,
                RestaurantNotFoundException.MSG_WITH_ID.formatted(TestUtils.NOT_FOUND),
                VoteController.REST_URL);
    }

    @Test
    @SneakyThrows
    void changeVote_ChangeVote_ReturnNotContent() {
        configureClockMockForTimeNotExceeds();

        mockMvc.perform(MockMvcUtils.changeVote(RestaurantTestData.BURGER_ID)
                        .with(SecurityUtils.userHttpBasic(UserTestData.user2)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @SneakyThrows
    void changeVote_VoteTimeExceeds_ReturnError() {
        configureClockMockForTimeExceeds();

        ResultActions resultActions = mockMvc.perform(MockMvcUtils.changeVote(RestaurantTestData.BURGER_ID)
                .with(SecurityUtils.userHttpBasic(UserTestData.user2)));

        ResultActionErrorsUtil.checkVoteLimitExceedError(resultActions,
                VotingTimeLimitExceedsException.VOTE_AFTER_LIMIT
                        .formatted(UserTestData.USER2_ID, limitTime, VoteTestData.VOTING_TIME_EXCEEDED.toLocalTime()));
    }

    @Test
    @SneakyThrows
    void vote_RestaurantNotFoundReVote_ReturnError() {
        configureClockMockForTimeNotExceeds();

        ResultActions resultActions = mockMvc.perform(MockMvcUtils.changeVote(TestUtils.NOT_FOUND)
                        .with(SecurityUtils.userHttpBasic(UserTestData.user1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        ResultActionErrorsUtil.checkNotFoundFields(resultActions,
                RestaurantNotFoundException.MSG_WITH_ID.formatted(TestUtils.NOT_FOUND),
                VoteController.REST_URL);
    }

    @Test
    @SneakyThrows
    void getTodayVote_AllOk_ReturnTodayVote() {
        configureClockMockForTimeNotExceeds();
        mockMvc.perform(MockMvcUtils.getTodayVote()
                        .with(SecurityUtils.userHttpBasic(UserTestData.user1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(VoteTestData.VOTE_DTO_MATCHER.contentJson(VoteTestData.user1VoteSushiDtoWithRestaurant));
    }

    @Test
    @SneakyThrows
    void getTodayVote_NoVote_ReturnNoContent() {
        configureClockMockForTimeNotExceeds();
        mockMvc.perform(MockMvcUtils.getTodayVote()
                        .with(SecurityUtils.userHttpBasic(UserTestData.user3)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @SneakyThrows
    void getAll_AllOk_ReturnTodayVote() {
        mockMvc.perform(MockMvcUtils.getAllVotes()
                        .with(SecurityUtils.userHttpBasic(UserTestData.user1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(VoteTestData.VOTE_DTO_MATCHER.contentJson(List.of(VoteTestData.user1VoteSushiDtoWithRestaurant)));
    }

    private void configureClockMockForTimeNotExceeds() {
        Mockito.when(clock.instant()).thenReturn(VoteTestData.VOTING_TIME.toInstant(ZoneOffset.UTC));
    }

    private void configureClockMockForTimeExceeds() {
        Mockito.when(clock.instant()).thenReturn(VoteTestData.VOTING_TIME_EXCEEDED.toInstant(ZoneOffset.UTC));
    }
}
