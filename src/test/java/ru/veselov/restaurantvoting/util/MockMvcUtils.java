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
import ru.veselov.restaurantvoting.web.VoteController;
import ru.veselov.restaurantvoting.web.dish.DishAdminController;
import ru.veselov.restaurantvoting.web.dish.DishController;
import ru.veselov.restaurantvoting.web.menu.MenuAdminController;
import ru.veselov.restaurantvoting.web.menu.MenuController;
import ru.veselov.restaurantvoting.web.restaurant.RestaurantAdminController;
import ru.veselov.restaurantvoting.web.restaurant.RestaurantController;
import ru.veselov.restaurantvoting.web.user.ProfileController;
import ru.veselov.restaurantvoting.web.user.UserAdminController;

import java.time.LocalDate;

@UtilityClass
public class MockMvcUtils {

    public static final String RESTAURANTS_WITH_MENU = RestaurantController.REST_URL + "/with-menu";

    public static final String RESTAURANTS_WITH_MENU_ID = RestaurantController.REST_URL + "/with-menu/%s";

    public static final String MENU_ID_URL = MenuAdminController.REST_URL + "/%s";

    public static final String DISH_ID_URL = DishAdminController.REST_URL + "/%s";

    public static final String RESTAURANT_URL = RestaurantAdminController.REST_URL;

    public static final String RESTAURANT_ID_URL = RestaurantAdminController.REST_URL + "/%s";

    public static final String USER_ID_URL = UserAdminController.REST_URL + "/%s";

    public static final String USER_EMAIL_URL = UserAdminController.REST_URL + "/by-email";

    public static final String REGISTER = ProfileController.REST_URL + "/register";

    public static final String VOTE_TODAY = VoteController.REST_URL + "/today";


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

    public static MockHttpServletRequestBuilder getOneRestaurantWithMenu(int id) {
        return MockMvcRequestBuilders.get(RESTAURANTS_WITH_MENU_ID.formatted(id));
    }

    public static MockHttpServletRequestBuilder getAllRestaurantsWithMenu() {
        return MockMvcRequestBuilders.get(RESTAURANTS_WITH_MENU);
    }

    public static MockHttpServletRequestBuilder createDish(int menuId, DishDto dishDto) {
        return MockMvcRequestBuilders.post(DishAdminController.REST_URL).param("menuId", String.valueOf(menuId))
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

    public static MockHttpServletRequestBuilder createMenu(int restaurantId, LocalDate date) {
        return MockMvcRequestBuilders.post(MenuAdminController.REST_URL)
                .param("restaurantId", String.valueOf(restaurantId))
                .param("date", String.valueOf(date));
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
        return MockMvcRequestBuilders.get(MenuController.REST_URL).param("restaurantId", String.valueOf(restaurantId));
    }

    public static MockHttpServletRequestBuilder deleteMenu(int id) {
        return MockMvcRequestBuilders.delete(MenuAdminController.REST_URL + "/" + id);
    }

    public static MockHttpServletRequestBuilder vote(int restaurantId) {
        return MockMvcRequestBuilders.post(VoteController.REST_URL).param("restaurantId", String.valueOf(restaurantId));
    }

    public static MockHttpServletRequestBuilder changeVote(int restaurantId) {
        return MockMvcRequestBuilders.put(VoteController.REST_URL).param("restaurantId", String.valueOf(restaurantId));
    }

    public static MockHttpServletRequestBuilder getTodayVote() {
        return MockMvcRequestBuilders.get(VOTE_TODAY);
    }

    public static MockHttpServletRequestBuilder getAllVotes() {
        return MockMvcRequestBuilders.get(VoteController.REST_URL);
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
