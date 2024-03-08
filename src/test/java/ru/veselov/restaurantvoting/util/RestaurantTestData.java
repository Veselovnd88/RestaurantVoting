package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.util.List;

@UtilityClass
public class RestaurantTestData {

    public static final int DB_COUNT = 3;

    public static Restaurant sushiRestaurant = new Restaurant(100003, "Sushiwok", List.of(MenuTestData.sushiRestaurantMenu));

    public static Restaurant pizzaRestaurant = new Restaurant(100004, "DoDoPizza", List.of(MenuTestData.pizzaRestaurantMenu));

    public static Restaurant burgerRestaurant = new Restaurant(100005, "BurgerPizza", List.of(MenuTestData.burgerRestaurantMenu));
}
