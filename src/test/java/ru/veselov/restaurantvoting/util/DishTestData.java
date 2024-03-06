package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.model.Dish;

import java.time.LocalDate;

@UtilityClass
public class DishTestData {

    public static Dish unagi = new Dish(100006, "Unagi", 40000, LocalDate.of(2024, 3, 5), RestaurantTestData.sushiRestaurant);

    public static Dish philadelphia = new Dish(100007, "Philadelphia", 30000, LocalDate.of(2024, 3, 5), RestaurantTestData.sushiRestaurant);

    public static Dish tastyRoll = new Dish(100007, "TastyRoll", 55000, LocalDate.of(2024, 3, 5), RestaurantTestData.sushiRestaurant);

}
