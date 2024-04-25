package ru.veselov.restaurantvoting.web.validaton;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.veselov.restaurantvoting.dto.InputRestaurantDto;
import ru.veselov.restaurantvoting.extension.argproviders.BadRestaurantArgumentsProvider;
import ru.veselov.restaurantvoting.service.restaurant.RestaurantService;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.web.GlobalExceptionHandler;
import ru.veselov.restaurantvoting.web.RestaurantAdminController;

@ActiveProfiles("test")
@WebMvcTest(value = RestaurantAdminController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
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
    @Test
    void create_RestaurantWithId_ReturnValidationError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createRestaurant(RestaurantTestData.restaurantDtoToUpdate));

        ResultActionErrorsUtil.checkRequestValidationError(resultActions,
                GlobalExceptionHandler.REQUEST_VALIDATION_FAILED,
                MockMvcUtils.RESTAURANT_URL);
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

    @SneakyThrows
    @Test
    void update_RestaurantWithoutId_ReturnValidationError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.updateRestaurant(
                RestaurantTestData.inputRestaurantDto, RestaurantTestData.SUSHI_ID));

        ResultActionErrorsUtil.checkRequestValidationError(resultActions,
                GlobalExceptionHandler.REQUEST_VALIDATION_FAILED,
                MockMvcUtils.RESTAURANT_ID_URL.formatted(RestaurantTestData.SUSHI_ID));
    }
}
