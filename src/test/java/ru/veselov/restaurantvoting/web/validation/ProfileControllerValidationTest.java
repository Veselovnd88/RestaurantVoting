package ru.veselov.restaurantvoting.web.validation;

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
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.extension.argproviders.BadUserDtoWithPasswordArgumentsProvider;
import ru.veselov.restaurantvoting.extension.argproviders.BadUserDtoWithoutNullPasswordArgumentsProvider;
import ru.veselov.restaurantvoting.service.user.UserService;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.util.UserTestData;
import ru.veselov.restaurantvoting.web.GlobalExceptionHandler;
import ru.veselov.restaurantvoting.web.user.ProfileController;
@ActiveProfiles("test")
@WebMvcTest(value = ProfileController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ProfileControllerValidationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @ParameterizedTest
    @ArgumentsSource(BadUserDtoWithPasswordArgumentsProvider.class)
    @SneakyThrows
    void register_BadDtos_ReturnValidationError(UserDto userDto, String fieldName) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.register(userDto));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.REGISTER, fieldName, 0);
    }

    @Test
    @SneakyThrows
    void register_UserWithId_ReturnValidationError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.register(UserTestData.userToCreateWithId));

        ResultActionErrorsUtil.checkRequestValidationError(resultActions,
                GlobalExceptionHandler.REQUEST_VALIDATION_FAILED, MockMvcUtils.REGISTER);
    }

    @ParameterizedTest
    @ArgumentsSource(BadUserDtoWithoutNullPasswordArgumentsProvider.class)
    @SneakyThrows
    void update_BadDtosWithoutPasswords_ReturnValidationError(UserDto userDto, String fieldName) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.updateUserProfile(userDto));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                ProfileController.REST_URL, fieldName, 0);
    }
}
