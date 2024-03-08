package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.time.LocalDate;

@UtilityClass
public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator("dishes", "restaurant");

    private final LocalDate LOCAL_DATE = LocalDate.of(2024, 3, 6);

    public static Menu sushiRestaurantMenu = new Menu(100006, LOCAL_DATE, RestaurantTestData.sushiRestaurant,
            DishTestData.unagi, DishTestData.philadelphia, DishTestData.tastyRoll);

    public static Menu pizzaRestaurantMenu = new Menu(100007, LOCAL_DATE, RestaurantTestData.pizzaRestaurant,
            DishTestData.pizzaArriva, DishTestData.diabloPizza, DishTestData.margarita);

    public static Menu burgerRestaurantMenu = new Menu(100008, LOCAL_DATE, RestaurantTestData.burgerRestaurant,
            DishTestData.doubleBurger, DishTestData.tripleBurger, DishTestData.friesPotato);
}
