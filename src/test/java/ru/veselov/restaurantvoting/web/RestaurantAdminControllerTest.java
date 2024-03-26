package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

@WithMockUser(value = "ADMIN")
class RestaurantAdminControllerTest extends AbstractRestControllerTest {

    @Test
    @SneakyThrows
    void create_AllOk_CreateRestaurantReturnDtoAndResourceUri() {
        mockMvc.perform(MockMvcUtils.createRestaurant(RestaurantTestData.newRestaurantDto))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(RestaurantTestData.RESTAURANT_DTO_MATCHER.contentJson(RestaurantTestData.savedRestaurantDto));
    }

    @Test
    @SneakyThrows
    void update_AllOk_UpdateRestaurantAndReturnUpdated() {
        mockMvc.perform(MockMvcUtils.updateRestaurant(RestaurantTestData.restaurantDtoToUpdate, RestaurantTestData.SUSHI_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(RestaurantTestData.RESTAURANT_DTO_MATCHER.contentJson(RestaurantTestData.sushiRestaurantUpdated));
    }

    @Test
    @SneakyThrows
    void delete_AllOk_DeleteRestaurantAndReturnNoContent() {
        mockMvc.perform(MockMvcUtils.deleteRestaurant(RestaurantTestData.SUSHI_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
