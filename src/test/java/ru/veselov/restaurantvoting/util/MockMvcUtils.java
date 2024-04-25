package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.dto.InputRestaurantDto;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.util.json.JsonUtil;
import ru.veselov.restaurantvoting.web.DishAdminController;
import ru.veselov.restaurantvoting.web.DishController;
import ru.veselov.restaurantvoting.web.MenuAdminController;
import ru.veselov.restaurantvoting.web.MenuController;
import ru.veselov.restaurantvoting.web.RestaurantAdminController;
import ru.veselov.restaurantvoting.web.RestaurantController;
import ru.veselov.restaurantvoting.web.VoteController;
import ru.veselov.restaurantvoting.web.user.ProfileController;
import ru.veselov.restaurantvoting.web.user.UserAdminController;

@UtilityClass
public class MockMvcUtils {

    public static final String MENU_RESTAURANTS_ID_URL = MenuAdminController.REST_URL + "/restaurants/%s";

    public static final String MENU_ID_URL = MenuAdminController.REST_URL + "/%s";

    public static final String DISH_MENUS_ID_URL = DishAdminController.REST_URL + "/menus/%s";

    public static final String DISH_ID_URL = DishAdminController.REST_URL + "/%s";

    public static final String RESTAURANT_URL = RestaurantAdminController.REST_URL;

    public static final String RESTAURANT_ID_URL = RestaurantAdminController.REST_URL + "/%s";

    public static final String USER_ID_URL = UserAdminController.REST_URL + "/%s";

    public static final String USER_EMAIL_URL = UserAdminController.REST_URL + "/by-email";

    public static final String VOTE_RESTAURANT_ID_URL = VoteController.REST_URL + "/restaurants/%s";

    public static final String REGISTER = ProfileController.REST_URL + "/register";


    public static MockHttpServletRequestBuilder createRestaurant(InputRestaurantDto restaurantDto) {
        return MockMvcRequestBuilders.post(RESTAURANT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurantDto));
    }

    public static MockHttpServletRequestBuilder updateRestaurant(InputRestaurantDto restaurantDto, int id) {
        return MockMvcRequestBuilders.put(RESTAURANT_ID_URL.formatted(id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurantDto));
    }

    public static MockHttpServletRequestBuilder deleteRestaurant(int id) {
        return MockMvcRequestBuilders.delete(RESTAURANT_ID_URL.formatted(id));
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
        return MockMvcRequestBuilders.post(DISH_MENUS_ID_URL.formatted(menuId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishDto));
    }

    public static MockHttpServletRequestBuilder updateDish(int id, DishDto dishDto) {
        return MockMvcRequestBuilders.put(DISH_ID_URL.formatted(id))
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

    public static MockHttpServletRequestBuilder createMenu(int restaurantId, InputMenuDto menuDto) {
        return MockMvcRequestBuilders.post(MENU_RESTAURANTS_ID_URL.formatted(restaurantId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menuDto));
    }

    public static MockHttpServletRequestBuilder updateMenu(int id, InputMenuDto menuDto) {
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

    public static MockHttpServletRequestBuilder vote(int restaurantId) {
        return MockMvcRequestBuilders.post(VOTE_RESTAURANT_ID_URL.formatted(restaurantId));
    }

    public static MockHttpServletRequestBuilder removeVote() {
        return MockMvcRequestBuilders.delete(VoteController.REST_URL);
    }

    public static MockHttpServletRequestBuilder createUser(UserDto userDto) {
        return MockMvcRequestBuilders.post(UserAdminController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userDto));
    }

    public static MockHttpServletRequestBuilder getUsers() {
        return MockMvcRequestBuilders.get(UserAdminController.REST_URL);
    }

    public static MockHttpServletRequestBuilder getUserById(int id) {
        return MockMvcRequestBuilders.get(USER_ID_URL.formatted(id));
    }

    public static MockHttpServletRequestBuilder getUserByEmail(String email) {
        return MockMvcRequestBuilders.get(USER_EMAIL_URL).param("email", email);
    }

    public static MockHttpServletRequestBuilder updateUser(int id, UserDto userDto) {
        return MockMvcRequestBuilders.put(USER_ID_URL.formatted(id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userDto));
    }

    public static MockHttpServletRequestBuilder deleteUserById(int id) {
        return MockMvcRequestBuilders.delete(USER_ID_URL.formatted(id));
    }

    public static MockHttpServletRequestBuilder changeUserStatus(int id, boolean enabled) {
        return MockMvcRequestBuilders.patch(USER_ID_URL.formatted(id)).param("enabled", String.valueOf(enabled));
    }


    public static MockHttpServletRequestBuilder register(UserDto userDto) {
        return MockMvcRequestBuilders.post(REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userDto));
    }

    public static MockHttpServletRequestBuilder getUserProfile() {
        return MockMvcRequestBuilders.get(ProfileController.REST_URL);
    }

    public static MockHttpServletRequestBuilder updateUserProfile(UserDto userDto) {
        return MockMvcRequestBuilders.put(ProfileController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userDto));
    }

    public static MockHttpServletRequestBuilder deleteUser() {
        return MockMvcRequestBuilders.delete(ProfileController.REST_URL);
    }
}
