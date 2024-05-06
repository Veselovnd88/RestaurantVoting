package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;

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
}
