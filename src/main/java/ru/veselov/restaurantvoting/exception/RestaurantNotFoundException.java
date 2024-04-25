package ru.veselov.restaurantvoting.exception;

import jakarta.persistence.EntityNotFoundException;

public class RestaurantNotFoundException extends EntityNotFoundException {
    public static final String MSG_WITH_ID = "Restaurant with [id: %s] not found";

    public RestaurantNotFoundException(int id) {
        super(MSG_WITH_ID.formatted(id));
    }
}
