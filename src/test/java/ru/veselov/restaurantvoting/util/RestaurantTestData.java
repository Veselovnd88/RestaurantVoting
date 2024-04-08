package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class RestaurantTestData {

    public static final int DB_COUNT = 3;

    public static final int SUSHI_ID = 100003;

    public static final int PIZZA_ID = 100004;

    public static final int BURGER_ID = 100005;

    public static final int NOT_FOUND = 200000;

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(Restaurant.class, "votes", "menus");

    public static final MatcherFactory.Matcher<RestaurantDto> RESTAURANT_DTO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(RestaurantDto.class);

    public static Restaurant sushiRestaurant = new Restaurant(SUSHI_ID, "SushiWok");

    public static Restaurant pizzaRestaurant = new Restaurant(PIZZA_ID, "DoDoPizza");

    public static Restaurant burgerRestaurant = new Restaurant(BURGER_ID, "BurgerPizza");

    public static NewRestaurantDto newRestaurantDto = new NewRestaurantDto("New tasty restaurant");

    public static NewRestaurantDto restaurantDtoToUpdate = new NewRestaurantDto("Updated tasty restaurant");

    public static RestaurantDto savedRestaurantDto = new RestaurantDto(100021, "New tasty restaurant");

    public static RestaurantDto sushiRestaurantDto = new RestaurantDto(100003, "SushiWok");

    public static RestaurantDto sushiRestaurantDtoWithEmptyMenus = new RestaurantDto(100003, "SushiWok", Collections.emptyList());

    public static RestaurantDto pizzaRestaurantDto = new RestaurantDto(100004, "DoDoPizza");

    public static RestaurantDto burgerRestaurantDto = new RestaurantDto(100005, "BurgerPizza");

    public static RestaurantDto sushiRestaurantUpdated = new RestaurantDto(100003, "Updated tasty restaurant");

    public static List<RestaurantDto> restaurantDtos = List.of(burgerRestaurantDto, pizzaRestaurantDto, sushiRestaurantDto);

    public static RestaurantDto getSushiRestaurantDtoWithMenuByDate() {
        RestaurantDto restaurantDto = new RestaurantDto(100003, "SushiWok");
        restaurantDto.setMenus(List.of(MenuTestData.sushiRestaurantMenuDtoWithVotes));
        return restaurantDto;
    }
}
