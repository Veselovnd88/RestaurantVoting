package ru.veselov.restaurantvoting.exception;

public class VotingTimeLimitExceedsException extends RuntimeException {
    public VotingTimeLimitExceedsException(String message) {
        super(message);
    }
}
