package ru.veselov.restaurantvoting.exception;

import java.time.LocalDate;

public class MenuConflictException extends ObjectAlreadyExistsException {
    public static final String MESSAGE_CONFLICT = "Menu of [restaurant: %s] for [date: %s] already exists";

    public MenuConflictException(int restaurantId, LocalDate date) {
        super(MESSAGE_CONFLICT.formatted(restaurantId, date));
    }
}
