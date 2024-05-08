package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.InputRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.util.List;

@UtilityClass
public class RestaurantTestData {

    public static final int DB_COUNT = 3;

    public static final int SUSHI_ID = TestUtils.START_SEQ + 3;

    public static final int PIZZA_ID = TestUtils.START_SEQ + 4;

    public static final int BURGER_ID = TestUtils.START_SEQ + 5;

    public static final int NEW_RESTAURANT_ID = TestUtils.START_SEQ + 22;

    public static final String SUSHI_WOK = "SushiWok";

    public static final String PIZZA = "DoDoPizza";

    public static final String BURGER = "BurgerPizza";

    public static final String NEW_TASTY_RESTAURANT = "New tasty restaurant";

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(Restaurant.class, "menus", "votes");

    public static final MatcherFactory.Matcher<RestaurantDto> RESTAURANT_DTO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(RestaurantDto.class);

    public static Restaurant sushiRestaurant = new Restaurant(SUSHI_ID, SUSHI_WOK);

    public static Restaurant pizzaRestaurant = new Restaurant(PIZZA_ID, PIZZA);

    public static Restaurant burgerRestaurant = new Restaurant(BURGER_ID, BURGER);

    public static InputRestaurantDto inputRestaurantDto = new InputRestaurantDto(null, NEW_TASTY_RESTAURANT);

    public static InputRestaurantDto restaurantDtoToUpdate = new InputRestaurantDto(SUSHI_ID, "Updated tasty restaurant");

    public static RestaurantDto savedRestaurantDto = new RestaurantDto(NEW_RESTAURANT_ID, NEW_TASTY_RESTAURANT, null);

    public static RestaurantDto sushiRestaurantDto = new RestaurantDto(SUSHI_ID, SUSHI_WOK, null);

    public static RestaurantDto pizzaRestaurantDto = new RestaurantDto(PIZZA_ID, PIZZA, null);

    public static RestaurantDto burgerRestaurantDto = new RestaurantDto(BURGER_ID, BURGER, null);

    public static RestaurantDto sushiRestaurantUpdated = new RestaurantDto(SUSHI_ID, "Updated tasty restaurant", null);

    public static List<RestaurantDto> restaurantDtos = List.of(burgerRestaurantDto, pizzaRestaurantDto, sushiRestaurantDto);

    public static RestaurantDto getSushiRestaurantDtoWithMenuByDate() {
        return new RestaurantDto(RestaurantTestData.SUSHI_ID, SUSHI_WOK,
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
