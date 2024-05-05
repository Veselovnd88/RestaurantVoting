package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.exception.RestaurantNotFoundException;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;

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
    void getWithMenuByDate_AllOk_ReturnOneRestaurantDtoWithMenu() {
        mockMvc.perform(MockMvcUtils.getOneRestaurantWithMenu(RestaurantTestData.SUSHI_ID).param("date", "2024-03-06"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(RestaurantTestData.RESTAURANT_DTO_MATCHER
                        .contentJson(RestaurantTestData.getSushiRestaurantDtoWithMenuByDate()));
    }

    @Test
    @SneakyThrows
    void getWithMenuByDate_NoMenus_ReturnNotFoundError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.getOneRestaurantWithMenu(RestaurantTestData.SUSHI_ID)
                .param("date", "2020-03-06"));

        ResultActionErrorsUtil.checkNotFoundFields(resultActions,
                RestaurantNotFoundException.MSG_WITH_ID_DATE.formatted(RestaurantTestData.SUSHI_ID, "2020-03-06"),
                MockMvcUtils.RESTAURANTS_WITH_MENU_ID.formatted(RestaurantTestData.SUSHI_ID));
    }

    @Test
    @SneakyThrows
    void getAllWithMenuByDate_NoMenuForDate_ReturnListOfRestaurantDtoWithMenu() {
        mockMvc.perform(MockMvcUtils.getAllRestaurantsWithMenu().param("date", "2024-03-06"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(RestaurantTestData.RESTAURANT_DTO_MATCHER
                        .contentJson(RestaurantTestData.getAllRestaurantDtosWithMenus()));
    }
}
