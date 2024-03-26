package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.util.json.JsonUtil;
import ru.veselov.restaurantvoting.web.RestaurantAdminController;

@UtilityClass
public class MockMvcUtils {

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
}
