package ru.veselov.restaurantvoting.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.veselov.restaurantvoting.dto.VoteDto;
import ru.veselov.restaurantvoting.exception.NotFoundException;
import ru.veselov.restaurantvoting.exception.VoteNotFoundException;
import ru.veselov.restaurantvoting.exception.VotingTimeLimitExceedsException;
import ru.veselov.restaurantvoting.mapper.VoteMapper;
import ru.veselov.restaurantvoting.mapper.VoteMapperImpl;
import ru.veselov.restaurantvoting.model.Vote;
import ru.veselov.restaurantvoting.repository.UserRepository;
import ru.veselov.restaurantvoting.repository.VoteRepository;
import ru.veselov.restaurantvoting.service.menu.MenuService;
import ru.veselov.restaurantvoting.service.vote.VoteServiceImpl;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.UserTestData;
import ru.veselov.restaurantvoting.util.VoteTestData;

import java.time.LocalTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VoteServiceImplTest {

    @Mock
    VoteRepository voteRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    MenuService menuService;

    @InjectMocks
    VoteServiceImpl voteService;

    @Captor
    ArgumentCaptor<Vote> voteCaptor;

    @BeforeEach
    void setup() {
        VoteMapperImpl voteMapper = new VoteMapperImpl();
        ReflectionTestUtils.setField(voteService, "voteMapper", voteMapper, VoteMapper.class);
        ReflectionTestUtils.setField(voteService, "limitTime", VoteTestData.LIMIT_TIME, LocalTime.class);
    }

    @Test
    void vote_NewVote_AddVote() {
        Mockito.when(userRepository.getReferenceById(UserTestData.USER1_ID)).thenReturn(UserTestData.user1);
        Mockito.when(menuService.findMenuByRestaurantIdAndLocalDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(MenuTestData.getSushiRestaurantMenuWithVotes());

        voteService.vote(UserTestData.USER1_ID, MenuTestData.SUSHI_MENU_ID, VoteTestData.VOTING_TIME);

        Mockito.verify(voteRepository, Mockito.times(1)).save(voteCaptor.capture());
        Vote vote = voteCaptor.getValue();
        VoteTestData.VOTE_MATCHER.assertMatch(vote, VoteTestData.user1VoteSushiPreSaved);
    }

    @Test
    void changeVote_AllOk_ChangeVoteForAnotherMenu() {
        Mockito.when(voteRepository.findByUserIdForDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(Optional.of(VoteTestData.getNewUser1VoteSushi()));
        Mockito.when(menuService.findMenuByRestaurantIdAndLocalDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(MenuTestData.burgerRestaurantMenu);

        voteService.changeVote(UserTestData.USER1_ID, MenuTestData.SUSHI_MENU_ID, VoteTestData.VOTING_TIME);

        Mockito.verify(voteRepository, Mockito.times(1)).save(voteCaptor.capture());
        Vote vote = voteCaptor.getValue();
        VoteTestData.VOTE_MATCHER.assertMatch(vote, VoteTestData.user1VoteSushi);
    }

    @Test
    void changeVote_VoteNotFound_ThrowException() {
        Mockito.when(voteRepository.findByUserIdForDate(Mockito.anyInt(), Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(VoteNotFoundException.class)
                .isThrownBy(
                        () -> voteService.changeVote(UserTestData.USER1_ID, MenuTestData.BURGER_MENU_ID, VoteTestData.VOTING_TIME))
                .withMessage(VoteNotFoundException
                        .MSG_USER_ID_LOCAL_DATE.formatted(UserTestData.USER1_ID, VoteTestData.VOTING_TIME.toLocalDate()))
                .isInstanceOf(NotFoundException.class);

        Mockito.verify(voteRepository, Mockito.never()).save(voteCaptor.capture());
    }

    @Test
    void changeVote_TimeExceedsLimit_ThrowException() {
        Assertions.assertThatThrownBy(() -> voteService.changeVote(UserTestData.USER1_ID, MenuTestData.BURGER_MENU_ID,
                        VoteTestData.VOTING_TIME_EXCEEDED))
                .isInstanceOf(VotingTimeLimitExceedsException.class);

        Mockito.verify(voteRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void getByUserIdForDate_AllOk_ReturnOptionalWithVoteDto() {
        Mockito.when(voteRepository.findByUserIdForDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(Optional.of(VoteTestData.getNewUser1VoteSushi()));

        Optional<VoteDto> optionalVoteDto = voteService
                .getByUserIdForDate(UserTestData.USER1_ID, VoteTestData.VOTED_AT_DATE);

        Assertions.assertThat(optionalVoteDto).isPresent()
                .map(voteDto -> voteDto).hasValue(VoteTestData.user1VoteSushiDtoWithRestaurant);
    }

    @Test
    void getByUserIdForDate_NoVotes_ReturnOptionalEmpty() {
        Mockito.when(voteRepository.findByUserIdForDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(Optional.empty());

        Optional<VoteDto> optionalVoteDto = voteService
                .getByUserIdForDate(UserTestData.USER1_ID, VoteTestData.VOTED_AT_DATE);

        Assertions.assertThat(optionalVoteDto).isEmpty();
    }
}
