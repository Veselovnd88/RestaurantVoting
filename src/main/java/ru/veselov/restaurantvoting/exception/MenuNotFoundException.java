package ru.veselov.restaurantvoting.exception;

import java.time.LocalDate;

public class MenuNotFoundException extends NotFoundException {

    public static final String MESSAGE_WITH_ID = "Menu with [id: %s] not found";
    public static final String MESSAGE_WITH_REST_ID_FOR_DATE = "Menu for restaurant: %s and date %s not found";

    public MenuNotFoundException(int restaurantId, LocalDate localDate) {
        super(MESSAGE_WITH_REST_ID_FOR_DATE.formatted(restaurantId, localDate));
    }

    public MenuNotFoundException(int id) {
        super(MESSAGE_WITH_ID.formatted(id));
    }
}
