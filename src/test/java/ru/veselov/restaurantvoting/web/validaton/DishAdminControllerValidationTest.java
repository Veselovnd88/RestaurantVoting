package ru.veselov.restaurantvoting.web.validaton;

import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.extension.argproviders.BadDishDtoArgumentsProvider;
import ru.veselov.restaurantvoting.service.DishService;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.web.DishAdminController;
import ru.veselov.restaurantvoting.web.GlobalExceptionHandler;

@WebMvcTest(value = DishAdminController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc
class DishAdminControllerValidationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DishService dishService;

    @SneakyThrows
    @ParameterizedTest
    @ArgumentsSource(BadDishDtoArgumentsProvider.class)
    void create_BadDishDtoPasses_ReturnError(DishDto dishDto, String fieldName) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createDish(MenuTestData.BURGER_MENU_ID, dishDto));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.DISH_MENUS_ID_URL.formatted(MenuTestData.BURGER_MENU_ID), fieldName, 0);
    }

    @SneakyThrows
    @ParameterizedTest
    @ArgumentsSource(BadDishDtoArgumentsProvider.class)
    void update_BadDishDtoPasses_ReturnError(DishDto dishDto, String fieldName) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.updateDish(DishTestData.TASTY_ROLL_ID, dishDto));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.DISH_ID_URL.formatted(DishTestData.TASTY_ROLL_ID), fieldName, 0);
    }
}
