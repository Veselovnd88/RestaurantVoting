package ru.veselov.restaurantvoting.exception;

import jakarta.persistence.EntityNotFoundException;

public class DishNotFoundException extends EntityNotFoundException {

    public static final String MSG_WITH_ID = "Dish with id: %s not found";

    public DishNotFoundException(int id) {
        super(MSG_WITH_ID.formatted(id));
    }
}
