package ru.veselov.restaurantvoting.exception;

import java.time.LocalDate;

public class RestaurantNotFoundException extends NotFoundException {

    public static final String MSG_WITH_ID = "Restaurant with [id: %s] not found";

    public static final String MSG_WITH_ID_DATE = MSG_WITH_ID + "or doesnt have menu for date [%s]";

    public RestaurantNotFoundException(int id) {
        super(MSG_WITH_ID.formatted(id));
    }

    public RestaurantNotFoundException(int id, LocalDate date) {
        super(MSG_WITH_ID_DATE.formatted(id, date));
    }
}
