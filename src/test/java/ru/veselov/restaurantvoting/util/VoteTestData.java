package ru.veselov.restaurantvoting.util;

import ru.veselov.restaurantvoting.dto.VoteDto;
import ru.veselov.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("user", "menu");

    public static final LocalDate VOTED_AT_DATE = LocalDate.of(2024, 3, 6);

    public static Vote user1VoteSushi = new Vote(100018, VOTED_AT_DATE, UserTestData.user1);

    public static Vote adminVoteSushi = new Vote(100019, VOTED_AT_DATE, UserTestData.admin);

    public static Vote user2VoteSushi = new Vote(100020, VOTED_AT_DATE, UserTestData.user2);

    public static List<Vote> sushiVotes = List.of(user1VoteSushi, adminVoteSushi, user2VoteSushi);

    public static VoteDto userOneVoteSushiDto = new VoteDto(VOTED_AT_DATE, UserTestData.user1Dto);
}
