package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@UtilityClass
public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            Menu.class, "restaurant", "votes", "dishes");

    public static final LocalDate ADDED_DATE = LocalDate.of(2024, 3, 6);

    public static final int SUSHI_MENU_ID = 100006;

    public static Menu sushiRestaurantMenu = new Menu(100006, ADDED_DATE, RestaurantTestData.sushiRestaurant,
            new LinkedHashSet<>(DishTestData.sushiDishes));

    public static MenuDto sushiRestaurantMenuDto = new MenuDto(100006, ADDED_DATE, DishTestData.sushiDishesDtos, VoteTestData.sushiVotesDto);

    public static Menu pizzaRestaurantMenu = new Menu(100007, ADDED_DATE, RestaurantTestData.pizzaRestaurant,
            DishTestData.diabloPizza, DishTestData.margarita, DishTestData.pizzaArriva);

    public static Menu burgerRestaurantMenu = new Menu(100008, ADDED_DATE, RestaurantTestData.burgerRestaurant,
            DishTestData.doubleBurger, DishTestData.friesPotato, DishTestData.tripleBurger);
}
