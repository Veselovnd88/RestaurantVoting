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
import ru.veselov.restaurantvoting.dto.InputRestaurantDto;
import ru.veselov.restaurantvoting.extension.argproviders.BadRestaurantArgumentsProvider;
import ru.veselov.restaurantvoting.service.RestaurantService;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.web.GlobalExceptionHandler;
import ru.veselov.restaurantvoting.web.RestaurantAdminController;

@WebMvcTest(value = RestaurantAdminController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc
class RestaurantAdminControllerValidationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RestaurantService restaurantService;

    @SneakyThrows
    @ParameterizedTest()
    @ArgumentsSource(BadRestaurantArgumentsProvider.class)
    void create_BadRestaurantDto_ReturnError(InputRestaurantDto restaurantDto, String fieldName) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createRestaurant(restaurantDto));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.RESTAURANT_URL, fieldName, 0);
    }

    @SneakyThrows
    @ParameterizedTest()
    @ArgumentsSource(BadRestaurantArgumentsProvider.class)
    void update_BadRestaurantDto_ReturnError(InputRestaurantDto restaurantDto, String fieldName) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils
                .updateRestaurant(restaurantDto, RestaurantTestData.SUSHI_ID));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.RESTAURANT_ID_URL.formatted(RestaurantTestData.SUSHI_ID), fieldName, 0);
    }

}
