package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

@WithMockUser
class RestaurantControllerTest extends AbstractRestControllerTest {

    @Test
    @SneakyThrows
    void getAll_AllOk_ReturnListOfRestaurantDtos() {
        mockMvc.perform(MockMvcUtils.getAllRestaurants())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(RestaurantTestData.RESTAURANT_DTO_MATCHER.contentJson(
                        RestaurantTestData.burgerRestaurantDto,
                        RestaurantTestData.pizzaRestaurantDto,
                        RestaurantTestData.sushiRestaurantDto)
                );
    }

    @Test
    @SneakyThrows
    void getOne_AllOk_ReturnOneRestaurantDto() {
        mockMvc.perform(MockMvcUtils.getOneRestaurant(RestaurantTestData.SUSHI_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(RestaurantTestData.RESTAURANT_DTO_MATCHER.contentJson(RestaurantTestData.sushiRestaurantDto));
    }

    @Test
    @SneakyThrows
    void getOne_AllOk_ReturnOneRestaurantDtoWithMenuAndVotes() {
        mockMvc.perform(MockMvcUtils.getOneRestaurantWithMenuAndVotes(RestaurantTestData.SUSHI_ID).param("date", "2024-03-06"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(RestaurantTestData.RESTAURANT_DTO_MATCHER.contentJson(RestaurantTestData.sushiRestaurantDto));
    }
}
