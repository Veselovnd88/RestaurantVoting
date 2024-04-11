package ru.veselov.restaurantvoting.exception;

import jakarta.persistence.EntityNotFoundException;

public class MenuNotFoundException extends EntityNotFoundException {
    public static final String MESSAGE_WITH_ID = "Menu with [id: %s] not found";

    public MenuNotFoundException(int id) {
        super(MESSAGE_WITH_ID.formatted(id));
    }
}
