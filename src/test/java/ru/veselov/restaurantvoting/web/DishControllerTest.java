package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

@WithMockUser
class DishControllerTest extends AbstractRestControllerTest {

    @Test
    @SneakyThrows
    void getOne_AllOk_ReturnDishDto() {
        mockMvc.perform(MockMvcUtils.getDish(DishTestData.TASTY_ROLL_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(DishTestData.DISH_DTO_MATCHER.contentJson(DishTestData.tastyRollDto));
    }

    @Test
    @SneakyThrows
    void getAll_AllOk_ReturnDishDto() {
        mockMvc.perform(MockMvcUtils.getAllDishesByRestaurantId(RestaurantTestData.SUSHI_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(DishTestData.DISH_DTO_MATCHER.contentJson(DishTestData.sushiDishesDtos));
    }

    @Test
    @SneakyThrows
    void getAllByRestaurants_AllOk_ReturnDishDto() {
        mockMvc.perform(MockMvcUtils.getAllDishes())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(DishTestData.DISH_DTO_MATCHER.contentJson(DishTestData.allDishes));
    }
}
