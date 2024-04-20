package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.util.json.JsonUtil;
import ru.veselov.restaurantvoting.web.DishAdminController;
import ru.veselov.restaurantvoting.web.DishController;
import ru.veselov.restaurantvoting.web.MenuAdminController;
import ru.veselov.restaurantvoting.web.MenuController;
import ru.veselov.restaurantvoting.web.RestaurantAdminController;
import ru.veselov.restaurantvoting.web.RestaurantController;
import ru.veselov.restaurantvoting.web.VoteController;

@UtilityClass
public class MockMvcUtils {

    public static final String MENU_RESTAURANTS_ID_URL = MenuAdminController.REST_URL + "/restaurants/%s";

    public static final String MENU_ID_URL = MenuAdminController.REST_URL + "/%s";

    public static MockHttpServletRequestBuilder createRestaurant(NewRestaurantDto restaurantDto) {
        return MockMvcRequestBuilders.post(RestaurantAdminController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurantDto));
    }

    public static MockHttpServletRequestBuilder updateRestaurant(NewRestaurantDto restaurantDto, int id) {
        return MockMvcRequestBuilders.put(RestaurantAdminController.REST_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurantDto));
    }

    public static MockHttpServletRequestBuilder deleteRestaurant(int id) {
        return MockMvcRequestBuilders.delete(RestaurantAdminController.REST_URL + "/" + id);
    }

    public static MockHttpServletRequestBuilder getAllRestaurants() {
        return MockMvcRequestBuilders.get(RestaurantController.REST_URL);
    }

    public static MockHttpServletRequestBuilder getOneRestaurant(int id) {
        return MockMvcRequestBuilders.get(RestaurantController.REST_URL + "/" + id);
    }

    public static MockHttpServletRequestBuilder getOneRestaurantWithMenuAndVotes(int id) {
        return MockMvcRequestBuilders.get(RestaurantController.REST_URL + "/" + id + "/with-menu");
    }

    public static MockHttpServletRequestBuilder createDish(int menuId, DishDto dishDto) {
        return MockMvcRequestBuilders.post(DishAdminController.REST_URL + "/menus/" + menuId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishDto));
    }

    public static MockHttpServletRequestBuilder updateDish(int id, DishDto dishDto) {
        return MockMvcRequestBuilders.put(DishAdminController.REST_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishDto));
    }

    public static MockHttpServletRequestBuilder deleteDish(int id) {
        return MockMvcRequestBuilders.delete(DishAdminController.REST_URL + "/" + id);
    }

    public static MockHttpServletRequestBuilder getDish(int id) {
        return MockMvcRequestBuilders.get(DishController.REST_URL + "/" + id);
    }

    public static MockHttpServletRequestBuilder getAllDishes() {
        return MockMvcRequestBuilders.get(DishController.REST_URL);
    }

    public static MockHttpServletRequestBuilder getAllDishesByRestaurantId(int id) {
        return MockMvcRequestBuilders.get(DishController.REST_URL + "/restaurants/" + id);
    }

    public static MockHttpServletRequestBuilder createMenu(int restaurantId, NewMenuDto menuDto) {
        return MockMvcRequestBuilders.post(MENU_RESTAURANTS_ID_URL.formatted(restaurantId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menuDto));
    }

    public static MockHttpServletRequestBuilder updateMenu(int id, NewMenuDto menuDto) {
        return MockMvcRequestBuilders.put(MENU_ID_URL.formatted(id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menuDto));
    }

    public static MockHttpServletRequestBuilder getMenu(int id) {
        return MockMvcRequestBuilders.get(MenuController.REST_URL + "/" + id);
    }

    public static MockHttpServletRequestBuilder getMenusByRestaurantId(int restaurantId) {
        return MockMvcRequestBuilders.get(MenuController.REST_URL + "/restaurants/" + restaurantId);
    }

    public static MockHttpServletRequestBuilder deleteMenu(int id) {
        return MockMvcRequestBuilders.delete(MenuAdminController.REST_URL + "/" + id);
    }

    public static MockHttpServletRequestBuilder vote(int menuId) {
        return MockMvcRequestBuilders.post(VoteController.REST_URL + "/menus/" + menuId);
    }

    public static MockHttpServletRequestBuilder removeVote() {
        return MockMvcRequestBuilders.delete(VoteController.REST_URL);
    }
}
