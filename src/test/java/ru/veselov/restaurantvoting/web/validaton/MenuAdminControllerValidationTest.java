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
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.extension.BadInputMenuDtoArgumentsProvider;
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

    @SneakyThrows
    @ParameterizedTest
    @ArgumentsSource(BadInputMenuDtoArgumentsProvider.class)
    void create_SimilarDishes_ReturnValidationError(InputMenuDto menuDto, String fieldName) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createMenu(RestaurantTestData.SUSHI_ID, menuDto));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.MENU_RESTAURANTS_ID_URL.formatted(RestaurantTestData.SUSHI_ID), fieldName, 0);
    }

    @SneakyThrows
    @ParameterizedTest
    @ArgumentsSource(BadInputMenuDtoArgumentsProvider.class)
    void update_SimilarDishes_ReturnValidationError(InputMenuDto menuDto, String fieldName) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.updateMenu(MenuTestData.SUSHI_MENU_ID, menuDto));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.MENU_ID_URL.formatted(MenuTestData.SUSHI_MENU_ID), fieldName, 0);
    }
}
