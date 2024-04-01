package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;

@WithMockUser(value = "ADMIN")
class DishAdminControllerTest extends AbstractRestControllerTest {

    @Test
    @SneakyThrows
    void create_AllOk_ReturnCreateDtoAndLocation() {
        mockMvc.perform(MockMvcUtils.createDish(DishTestData.newTastyDish))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(DishTestData.DISH_DTO_MATCHER.contentJson(DishTestData.savedNewTastyDish));
    }

}