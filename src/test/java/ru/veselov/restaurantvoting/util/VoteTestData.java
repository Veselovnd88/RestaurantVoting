package ru.veselov.restaurantvoting.util;

import ru.veselov.restaurantvoting.dto.VoteDto;
import ru.veselov.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER_WITH_USER_VOTES = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final MatcherFactory.Matcher<VoteDto> VOTE_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteDto.class);

    public static final LocalTime LIMIT_TIME = LocalTime.of(11, 0, 0);

    public static final LocalDate VOTED_AT_DATE = LocalDate.of(2024, 3, 6);

    public static final LocalDateTime VOTING_TIME = LocalDateTime.of(VOTED_AT_DATE, LIMIT_TIME.minusHours(1));

    public static final LocalDateTime VOTING_TIME_EXCEEDED = LocalDateTime.of(VOTED_AT_DATE, LIMIT_TIME.plusHours(1));

    public static Vote user1VoteSushi = new Vote(100018, VOTED_AT_DATE, UserTestData.user1);

    public static Vote user1VoteSushiPreSaved = new Vote(null, VOTED_AT_DATE, UserTestData.user1);

    public static Vote adminVoteSushi = new Vote(100019, VOTED_AT_DATE, UserTestData.admin);

    public static Vote user2VoteSushi = new Vote(100020, VOTED_AT_DATE, UserTestData.user2);

    public static List<Vote> sushiVotes = List.of(user1VoteSushi, adminVoteSushi, user2VoteSushi);

    public static VoteDto user1VoteSushiDto = new VoteDto(null, VOTED_AT_DATE);

    public static VoteDto user2VoteSushiDto = new VoteDto(null, VOTED_AT_DATE);

    public static VoteDto user3VoteBurgerDto = new VoteDto(RestaurantTestData.BURGER_ID, VOTED_AT_DATE);

    public static VoteDto adminVoteSushiDto = new VoteDto(null, VOTED_AT_DATE);

    public static VoteDto user1VoteSushiDtoWithRestaurant = new VoteDto(RestaurantTestData.SUSHI_ID, VOTED_AT_DATE);

    public static List<VoteDto> sushiVotesDto = List.of(user1VoteSushiDto, adminVoteSushiDto, user2VoteSushiDto);

    public static Vote getNewUser1VoteSushi() {
        return new Vote(100018, VOTED_AT_DATE, UserTestData.user1,RestaurantTestData.sushiRestaurant);
    }

    public static Vote getUpdatedUser1VoteBurger() {
        return new Vote(100018, VOTED_AT_DATE, UserTestData.user1);
    }
}
