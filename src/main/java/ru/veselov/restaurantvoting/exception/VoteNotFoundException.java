package ru.veselov.restaurantvoting.exception;

import java.time.LocalDate;

public class VoteNotFoundException extends NotFoundException {

    public static final String MSG_USER_ID_LOCAL_DATE = "Vote for user [%s] for date [%s] not found";

    public VoteNotFoundException(int userId, LocalDate localDate) {
        super(MSG_USER_ID_LOCAL_DATE.formatted(userId, localDate));
    }
}
