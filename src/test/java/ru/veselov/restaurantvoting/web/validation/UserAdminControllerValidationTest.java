package ru.veselov.restaurantvoting.web.validation;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
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
import ru.veselov.restaurantvoting.util.SecurityUtils;
import ru.veselov.restaurantvoting.util.TestUtils;
import ru.veselov.restaurantvoting.util.UserTestData;
import ru.veselov.restaurantvoting.web.GlobalExceptionHandler;
import ru.veselov.restaurantvoting.web.user.UserAdminController;

import java.util.stream.Stream;

@ActiveProfiles("test")
@WebMvcTest(value = UserAdminController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserAdminControllerValidationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @ParameterizedTest
    @ArgumentsSource(BadUserDtoWithPasswordArgumentsProvider.class)
    @SneakyThrows
    void createWithLocation_BadDtos_ReturnValidationError(UserDto userDto, String fieldName) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createUser(userDto));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                UserAdminController.REST_URL, fieldName, 0);
    }

    @Test
    @SneakyThrows
    void createWithLocation_UserWithId_ReturnValidationError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createUser(UserTestData.userToCreateWithId));

        ResultActionErrorsUtil.checkRequestValidationError(resultActions,
                GlobalExceptionHandler.REQUEST_VALIDATION_FAILED, UserAdminController.REST_URL);
    }

    @ParameterizedTest
    @ArgumentsSource(BadUserDtoWithoutNullPasswordArgumentsProvider.class)
    @SneakyThrows
    void update_BadDtosWithoutPasswords_ReturnValidationError(UserDto userDto, String fieldName) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.updateUser(1, userDto));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.USER_ID_URL.formatted(1), fieldName, 0);
    }

    @Test
    @SneakyThrows
    void update_DifferentId_ReturnValidationError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.updateUser(TestUtils.NOT_FOUND,
                        UserTestData.user2Dto)
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkRequestValidationError(resultActions,
                GlobalExceptionHandler.REQUEST_VALIDATION_FAILED,
                MockMvcUtils.USER_ID_URL.formatted(TestUtils.NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("getBadEmail")
    @SneakyThrows
    void getByEmail_BadEmails_ReturnValidationError(String badEmail) {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.getUserByEmail(badEmail));

        ResultActionErrorsUtil.checkValidationError(resultActions,
                GlobalExceptionHandler.FIELDS_VALIDATION_FAILED,
                MockMvcUtils.USER_EMAIL_URL, "email", 0);
    }

    private static Stream<String> getBadEmail() {
        return Stream.of("asdf", "asdf".repeat(100).concat("@gmail.com"));
    }
}
