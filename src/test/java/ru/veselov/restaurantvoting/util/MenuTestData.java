package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class MenuTestData {

    private final LocalDate LOCAL_DATE = LocalDate.of(2024, 3, 6);

    public static Menu sushiRestaurantMenu = new Menu(LOCAL_DATE, RestaurantTestData.sushiRestaurant,
            List.of(DishTestData.unagi, DishTestData.philadelphia, DishTestData.tastyRoll));

    public static Menu pizzaRestaurantMenu = new Menu(LOCAL_DATE, RestaurantTestData.pizzaRestaurant,
            List.of(DishTestData.pizzaArriva, DishTestData.diabloPizza, DishTestData.margarita));

    public static Menu burgerRestaurantMenu = new Menu(LOCAL_DATE, RestaurantTestData.burgerRestaurant,
            List.of(DishTestData.doubleBurger, DishTestData.tripleBurger, DishTestData.friesPotato));
}
