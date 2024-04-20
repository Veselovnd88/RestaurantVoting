package ru.veselov.restaurantvoting.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public static final String MESSAGE_WITH_ID = "User with id: %s not found";

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(int id) {
        super(MESSAGE_WITH_ID.formatted(id));
    }
}
