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
import ru.veselov.restaurantvoting.exception.UserNotFoundException;
import ru.veselov.restaurantvoting.exception.VotingTimeLimitExceedsException;
import ru.veselov.restaurantvoting.extension.AdjustClockMockForVoting;
import ru.veselov.restaurantvoting.mapper.UserMapper;
import ru.veselov.restaurantvoting.mapper.UserMapperImpl;
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
        ReflectionTestUtils.setField(voteMapper, "userMapper", new UserMapperImpl(), UserMapper.class);
        ReflectionTestUtils.setField(voteService, "voteMapper", voteMapper, VoteMapper.class);
    }

    @Test
    @AdjustClockMockForVoting
    void vote_NewVoteForMenu_AddVote() {
        Mockito.when(voteRepository.findByUserIdForDate(Mockito.anyInt(), Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(UserTestData.USER1_ID)).thenReturn(Optional.of(UserTestData.user1));
        Mockito.when(menuService.findMenuByRestaurantIdAndLocalDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(MenuTestData.getSushiRestaurantMenuWithVotes());

        voteService.vote(UserTestData.USER1_ID, MenuTestData.SUSHI_MENU_ID, VoteTestData.VOTED_AT_DATE);

        Mockito.verify(voteRepository, Mockito.times(1)).save(voteCaptor.capture());
        Vote vote = voteCaptor.getValue();
        VoteTestData.VOTE_MATCHER_WITH_USER_VOTES.assertMatch(vote, VoteTestData.user1VoteSushiPreSaved);
    }

    @Test
    void vote_UserNotFound_ThrowException() {
        Mockito.when(voteRepository.findByUserIdForDate(Mockito.anyInt(), Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(UserTestData.USER1_ID)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(
                        () -> voteService.vote(UserTestData.USER1_ID, MenuTestData.SUSHI_MENU_ID, VoteTestData.VOTED_AT_DATE))
                .withMessage(UserNotFoundException.MESSAGE_WITH_ID.formatted(UserTestData.USER1_ID))
                .isInstanceOf(NotFoundException.class);

        Mockito.verify(voteRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @AdjustClockMockForVoting
    void vote_AlreadyVotedForAnotherMenuTimeDoesntExceedsLimit_MakeAnotherVote() {
        Mockito.when(voteRepository.findByUserIdForDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(Optional.of(VoteTestData.getNewUser1VoteSushi()));
        Mockito.when(menuService.findMenuByRestaurantIdAndLocalDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(MenuTestData.burgerRestaurantMenu);

        voteService.vote(UserTestData.USER1_ID, MenuTestData.BURGER_MENU_ID, VoteTestData.VOTED_AT_DATE);

        Mockito.verify(voteRepository, Mockito.times(1)).save(voteCaptor.capture());
        Vote vote = voteCaptor.getValue();
        VoteTestData.VOTE_MATCHER_WITH_USER_VOTES.assertMatch(vote, VoteTestData.getUpdatedUser1VoteBurger());
    }

    @Test
    @AdjustClockMockForVoting(exceeds = true)
    void vote_TimeExceedsLimit_ThrowException() {
        Mockito.when(voteRepository.findByUserIdForDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(Optional.of(VoteTestData.getNewUser1VoteSushi()));

        Assertions.assertThatThrownBy(() -> voteService.vote(UserTestData.USER1_ID, MenuTestData.BURGER_MENU_ID, VoteTestData.VOTED_AT_DATE))
                .isInstanceOf(VotingTimeLimitExceedsException.class);
        Mockito.verify(voteRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @AdjustClockMockForVoting
    void removeVote_AllOk_RemoveVote() {
        voteService.removeVote(UserTestData.USER1_ID, VoteTestData.VOTED_AT_DATE);
        Mockito.verify(voteRepository).deleteByUserIdForDate(Mockito.anyInt(), Mockito.any());
    }

    @Test
    @AdjustClockMockForVoting(exceeds = true)
    void removeVote_TimeExceeds_ThrowException() {
        Assertions.assertThatThrownBy(() -> voteService.removeVote(UserTestData.USER1_ID, VoteTestData.VOTED_AT_DATE))
                .isInstanceOf(VotingTimeLimitExceedsException.class);
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
