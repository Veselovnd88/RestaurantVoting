package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.model.Menu;

import java.time.LocalDate;

@UtilityClass
public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator("dishes", "restaurant");

    public final LocalDate MENU_ADDED_DATE = LocalDate.of(2024, 3, 6);

    public static Menu sushiRestaurantMenu = new Menu(100006, MENU_ADDED_DATE, RestaurantTestData.sushiRestaurant,
            DishTestData.philadelphia, DishTestData.tastyRoll, DishTestData.unagi);

    public static Menu pizzaRestaurantMenu = new Menu(100007, MENU_ADDED_DATE, RestaurantTestData.pizzaRestaurant,
            DishTestData.diabloPizza, DishTestData.margarita, DishTestData.pizzaArriva);

    public static Menu burgerRestaurantMenu = new Menu(100008, MENU_ADDED_DATE, RestaurantTestData.burgerRestaurant,
            DishTestData.doubleBurger, DishTestData.friesPotato, DishTestData.tripleBurger);
}
