package ru.veselov.restaurantvoting.web.validaton;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.veselov.restaurantvoting.service.MenuService;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.web.GlobalExceptionHandler;
import ru.veselov.restaurantvoting.web.MenuAdminController;

@WebMvcTest(value = MenuAdminController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc
class MenuAdminControllerValidationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MenuService menuService;

    @Test
    @SneakyThrows
    void create_SimilarDishes_ReturnValidationError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createMenu(RestaurantTestData.SUSHI_ID,
                        MenuTestData.menuDtoToCreateSimilarDishes))
                .andDo(MockMvcResultHandlers.print());

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.MENU_RESTAURANTS_ID_URL.formatted(RestaurantTestData.SUSHI_ID), "dishes", 0);
    }

    @Test
    @SneakyThrows
    void update_SimilarDishes_ReturnValidationError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.updateMenu(MenuTestData.SUSHI_MENU_ID,
                        MenuTestData.menuDtoToUpdateWithSimilarDishes))
                .andDo(MockMvcResultHandlers.print());

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.MENU_ID_URL.formatted(MenuTestData.SUSHI_MENU_ID), "dishes", 0);
    }
}
