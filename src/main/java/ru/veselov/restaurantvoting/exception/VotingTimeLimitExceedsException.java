package ru.veselov.restaurantvoting.exception;

import java.time.LocalTime;

public class VotingTimeLimitExceedsException extends RuntimeException {

    public static final String VOTE_AFTER_LIMIT = "User [id: %s] attempt to vote after %s, current time: %s";

    public VotingTimeLimitExceedsException(int id, LocalTime limitTime, LocalTime now) {
        super(VOTE_AFTER_LIMIT.formatted(id, limitTime, now));
    }
}
