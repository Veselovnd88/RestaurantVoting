package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.model.Restaurant;

@UtilityClass
public class RestaurantTestData {

    public static final int DB_COUNT = 3;

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER_NO_VOTES = MatcherFactory
            .usingIgnoringFieldsComparator("votes", "menus");

    public static Restaurant sushiRestaurant = new Restaurant(100003, "SushiWok");

    public static Restaurant pizzaRestaurant = new Restaurant(100004, "DoDoPizza");

    public static Restaurant burgerRestaurant = new Restaurant(100005, "BurgerPizza");

}
