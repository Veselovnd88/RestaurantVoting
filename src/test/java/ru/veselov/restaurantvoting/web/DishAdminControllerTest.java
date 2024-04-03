package ru.veselov.restaurantvoting.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.service.DishService;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;

@WithMockUser(value = "ADMIN")
class DishAdminControllerTest extends AbstractRestControllerTest {

    @Autowired
    DishService dishService;

    @Test
    @SneakyThrows
    void create_AllOk_ReturnCreateDtoAndLocation() {
        mockMvc.perform(MockMvcUtils.createDish(MenuTestData.SUSHI_MENU_ID, DishTestData.newTastyDish))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(DishTestData.DISH_DTO_MATCHER.contentJson(DishTestData.savedNewTastyDish));
    }

    @Test
    @SneakyThrows
    void update_AllOk_ReturnUpdatedDish() {
        mockMvc.perform(MockMvcUtils.updateDish(DishTestData.TASTY_ROLL_ID, DishTestData.dishToUpdate))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(DishTestData.DISH_DTO_MATCHER.contentJson(DishTestData.dishToUpdate));

        DishTestData.DISH_DTO_MATCHER.assertMatch(dishService.findOne(DishTestData.TASTY_ROLL_ID), DishTestData.dishToUpdate);
    }

    @Test
    @SneakyThrows
    void delete_AllOk_ReturnNoContent() {
        mockMvc.perform(MockMvcUtils.deleteDish(DishTestData.TASTY_ROLL_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThatException().isThrownBy(() -> dishService.findOne(DishTestData.TASTY_ROLL_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

}