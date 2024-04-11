package ru.veselov.restaurantvoting.exception;

public class ObjectAlreadyExistsException extends RuntimeException {

    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}
