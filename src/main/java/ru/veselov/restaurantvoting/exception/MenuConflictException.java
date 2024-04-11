package ru.veselov.restaurantvoting.exception;

public class MenuConflictException extends ObjectAlreadyExistsException {
    public MenuConflictException(String message) {
        super(message);
    }
}
