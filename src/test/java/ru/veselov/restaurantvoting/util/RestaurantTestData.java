package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.util.List;

@UtilityClass
public class RestaurantTestData {

    public static final int DB_COUNT = 3;

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER_NO_VOTES_NO_MENUS = MatcherFactory
            .usingIgnoringFieldsComparator("votes", "menus");

    public static Restaurant sushiRestaurant = new Restaurant(100003, "SushiWok");

    public static Restaurant pizzaRestaurant = new Restaurant(100004, "DoDoPizza");

    public static Restaurant burgerRestaurant = new Restaurant(100005, "BurgerPizza");

    public static Restaurant newRestaurant = new Restaurant(100022, "New tasty restaurant");

    public static NewRestaurantDto newRestaurantDto = new NewRestaurantDto("New tasty restaurant");

    public static RestaurantDto savedRestaurantDto = new RestaurantDto(100022, "New tasty restaurant", 0);

    public static RestaurantDto sushiRestaurantDto = new RestaurantDto(100003, "SushiWok", 1);

    public static RestaurantDto pizzaRestaurantDto = new RestaurantDto(100004, "DoDoPizza", 3);

    public static RestaurantDto burgerRestaurantDto = new RestaurantDto(100005, "BurgerPizza", 0);

    public static List<RestaurantDto> restaurantDtos = List.of(burgerRestaurantDto, pizzaRestaurantDto, sushiRestaurantDto);


}
