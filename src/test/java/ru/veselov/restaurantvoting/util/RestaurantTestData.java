package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.util.List;

@UtilityClass
public class RestaurantTestData {

    public static final int DB_COUNT = 3;

    public static final int SUSHI_ID = 100003;

    public static final int PIZZA_ID = 100004;

    public static final int BURGER_ID = 100005;

    public static final int NOT_FOUND = 200000;

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER_NO_VOTES_NO_MENUS = MatcherFactory
            .usingIgnoringFieldsComparator("votes", "menus");

    public static Restaurant sushiRestaurant = new Restaurant(SUSHI_ID, "SushiWok");

    public static Restaurant pizzaRestaurant = new Restaurant(PIZZA_ID, "DoDoPizza");

    public static Restaurant burgerRestaurant = new Restaurant(BURGER_ID, "BurgerPizza");

    public static Restaurant newRestaurant = new Restaurant(100022, "New tasty restaurant");

    public static NewRestaurantDto newRestaurantDto = new NewRestaurantDto("New tasty restaurant");

    public static NewRestaurantDto restaurantDtoToUpdate = new NewRestaurantDto("Updated tasty restaurant");

    public static RestaurantDto savedRestaurantDto = new RestaurantDto(100022, "New tasty restaurant", 0);

    public static RestaurantDto sushiRestaurantDto = new RestaurantDto(100003, "SushiWok", 1);

    public static RestaurantDto pizzaRestaurantDto = new RestaurantDto(100004, "DoDoPizza", 3);

    public static RestaurantDto burgerRestaurantDto = new RestaurantDto(100005, "BurgerPizza", 0);

    public static RestaurantDto sushiRestaurantUpdated = new RestaurantDto(100003, "Updated tasty restaurant", 1);

    public static List<RestaurantDto> restaurantDtos = List.of(burgerRestaurantDto, pizzaRestaurantDto, sushiRestaurantDto);


}
