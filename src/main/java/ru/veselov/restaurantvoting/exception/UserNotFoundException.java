package ru.veselov.restaurantvoting.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public static final String MESSAGE_WITH_ID = "User with id: %s not found";
    public static final String MESSAGE_WITH_EMAIL = "User with email: %s not found";

    public UserNotFoundException(int id) {
        super(MESSAGE_WITH_ID.formatted(id));
    }

    public UserNotFoundException(String email) {
        super(MESSAGE_WITH_EMAIL.formatted(email));
    }
}
