package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.InputRestaurantDto;
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
            .usingIgnoringFieldsComparator(Restaurant.class, "menus");

    public static final MatcherFactory.Matcher<RestaurantDto> RESTAURANT_DTO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(RestaurantDto.class);

    public static final String SUSHI_WOK = "SushiWok";
    public static Restaurant sushiRestaurant = new Restaurant(SUSHI_ID, SUSHI_WOK);

    public static final String PIZZA = "DoDoPizza";
    public static Restaurant pizzaRestaurant = new Restaurant(PIZZA_ID, PIZZA);

    public static final String BURGER = "BurgerPizza";
    public static Restaurant burgerRestaurant = new Restaurant(BURGER_ID, BURGER);

    public static InputRestaurantDto inputRestaurantDto = new InputRestaurantDto(null, "New tasty restaurant");

    public static InputRestaurantDto restaurantDtoToUpdate = new InputRestaurantDto(SUSHI_ID, "Updated tasty restaurant");

    public static RestaurantDto savedRestaurantDto = new RestaurantDto(100022, "New tasty restaurant", null);

    public static RestaurantDto sushiRestaurantDto = new RestaurantDto(100003, SUSHI_WOK, null);

    public static RestaurantDto sushiRestaurantDtoWithEmptyMenus = new RestaurantDto(100003, SUSHI_WOK, Collections.emptyList());

    public static RestaurantDto pizzaRestaurantDto = new RestaurantDto(100004, PIZZA, null);

    public static RestaurantDto burgerRestaurantDto = new RestaurantDto(100005, BURGER, null);

    public static RestaurantDto sushiRestaurantUpdated = new RestaurantDto(100003, "Updated tasty restaurant", null);

    public static List<RestaurantDto> restaurantDtos = List.of(burgerRestaurantDto, pizzaRestaurantDto, sushiRestaurantDto);

    public static RestaurantDto getSushiRestaurantDtoWithMenuByDate() {
        return new RestaurantDto(RestaurantTestData.SUSHI_ID, SUSHI_WOK,
                List.of(MenuTestData.sushiRestaurantMenuDto));
    }

    public static RestaurantDto getPizzaRestaurantDtoWithMenuByDate() {
        return new RestaurantDto(RestaurantTestData.PIZZA_ID, PIZZA,
                List.of(MenuTestData.sushiRestaurantMenuDto));
    }

    public static List<RestaurantDto> getAllRestaurantDtosWithMenus() {
        return List.of(
                new RestaurantDto(RestaurantTestData.BURGER_ID, BURGER, List.of(MenuTestData.burgerRestaurantMenuDto)),
                new RestaurantDto(RestaurantTestData.PIZZA_ID, PIZZA, List.of(MenuTestData.pizzaRestaurantMenuDto)),
                new RestaurantDto(RestaurantTestData.SUSHI_ID, SUSHI_WOK, List.of(MenuTestData.sushiRestaurantMenuDto))
        );
    }
}
