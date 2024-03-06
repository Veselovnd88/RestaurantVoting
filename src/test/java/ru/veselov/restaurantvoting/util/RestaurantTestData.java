package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.util.List;

@UtilityClass
public class RestaurantTestData {

    public static Restaurant sushiRestaurant = new Restaurant(100003, "Sushiwok", List.of(
            DishTestData.unagi, DishTestData.philadelphia, DishTestData.tastyRoll));
}
