package ru.veselov.restaurantvoting.web.user;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.exception.UserNotFoundException;
import ru.veselov.restaurantvoting.model.User;
import ru.veselov.restaurantvoting.repository.UserRepository;
import ru.veselov.restaurantvoting.service.user.UserService;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.util.SecurityUtils;
import ru.veselov.restaurantvoting.util.TestUtils;
import ru.veselov.restaurantvoting.util.UserTestData;
import ru.veselov.restaurantvoting.web.AbstractRestControllerTest;
import ru.veselov.restaurantvoting.web.GlobalExceptionHandler;

import java.util.List;
import java.util.Optional;

class UserAdminControllerTest extends AbstractRestControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @SneakyThrows
    void createWithLocation_AllOk_CreateNewUser() {
        mockMvc.perform(MockMvcUtils.createUser(UserTestData.userToCreate)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(UserTestData.USER_DTO_MATCHER.contentJson(UserTestData.userAfterCreate));
    }

    @Test
    @SneakyThrows
    void createWithLocation_EmailDuplicate_ReturnError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createUser(UserTestData.userToCreateWithConflict)
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkConflictError(resultActions, GlobalExceptionHandler.USER_WITH_EMAIL_EXISTS,
                UserAdminController.REST_URL);
    }

    @Test
    @SneakyThrows
    void getUsers_AllOk_ReturnListOfDto() {
        mockMvc.perform(MockMvcUtils.getUsers().with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(UserTestData.USER_DTO_MATCHER.contentJson(
                        List.of(UserTestData.adminDto, UserTestData.user1Dto, UserTestData.user2Dto, UserTestData.user3Dto)));
    }

    @Test
    @SneakyThrows
    void getById_AllOk_ReturnUserDto() {
        mockMvc.perform(MockMvcUtils.getUserById(UserTestData.USER1_ID)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(UserTestData.USER_DTO_MATCHER.contentJson(UserTestData.user1Dto));
    }

    @Test
    @SneakyThrows
    void getById_UserNotFound_ReturnError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.getUserById(TestUtils.NOT_FOUND)
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkNotFoundFields(resultActions,
                UserNotFoundException.MESSAGE_WITH_ID.formatted(TestUtils.NOT_FOUND),
                MockMvcUtils.USER_ID_URL.formatted(TestUtils.NOT_FOUND));
    }

    @Test
    @SneakyThrows
    void getByEmailAllOk_ReturnUserDto() {
        mockMvc.perform(MockMvcUtils.getUserByEmail(UserTestData.adminDto.email())
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(UserTestData.USER_DTO_MATCHER.contentJson(UserTestData.adminDto));
    }

    @Test
    @SneakyThrows
    void getByEmail_UserNotFound_ReturnError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.getUserByEmail("asd@asdf.com")
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkNotFoundFields(resultActions,
                UserNotFoundException.MESSAGE_WITH_EMAIL.formatted("asd@asdf.com"),
                MockMvcUtils.USER_EMAIL_URL);
    }

    @Test
    @SneakyThrows
    void update_AllOK_UpdateUser() {
        mockMvc.perform(MockMvcUtils.updateUser(UserTestData.USER2_ID, UserTestData.user2ToUpdate)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThat(userService.getById(UserTestData.USER2_ID)).isEqualTo(UserTestData.user2DtoUpdated);
    }

    @Test
    @SneakyThrows
    void update_EmailDuplicate_ReturnError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.updateUser(UserTestData.USER2_ID, UserTestData.user2ToUpdateForConflict)
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkConflictError(resultActions, GlobalExceptionHandler.USER_WITH_EMAIL_EXISTS,
                MockMvcUtils.USER_ID_URL.formatted(UserTestData.USER2_ID));
    }

    @Test
    @SneakyThrows
    void update_AllOKWithChangingPassword_UpdateUser() {
        mockMvc.perform(MockMvcUtils.updateUser(UserTestData.USER2_ID, UserTestData.user2ToUpdateWithPass)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThat(userService.getById(UserTestData.USER2_ID)).isEqualTo(UserTestData.user2DtoUpdated);
        Optional<User> byId = userRepository.findById(UserTestData.USER2_ID);
        Assertions.assertThat(byId).isPresent();
        passwordEncoder.matches(byId.get().getPassword(),
                passwordEncoder.encode(UserTestData.user2ToUpdateWithPass.password()));
    }

    @Test
    @SneakyThrows
    void update_UserNotFound_ReturnError() {
        UserDto userNotFoundToUpdate = new UserDto(TestUtils.NOT_FOUND, "UsernfUpd", "usernfUpd@gmail.com", null);
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.updateUser(TestUtils.NOT_FOUND, userNotFoundToUpdate)
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkNotFoundFields(resultActions,
                UserNotFoundException.MESSAGE_WITH_ID.formatted(TestUtils.NOT_FOUND),
                MockMvcUtils.USER_ID_URL.formatted(TestUtils.NOT_FOUND));
    }

    @Test
    @SneakyThrows
    void deleteById_AllOk_DeleteUser() {
        mockMvc.perform(MockMvcUtils.deleteUserById(UserTestData.USER2_ID)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThat(userRepository.findById(UserTestData.USER2_ID)).isEmpty();
    }

    @Test
    @SneakyThrows
    void enable_AllOk_ChangeUserStatus() {
        mockMvc.perform(MockMvcUtils.changeUserStatus(UserTestData.USER2_ID, false)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThat(userRepository.findById(UserTestData.USER2_ID)).isPresent()
                .map(User::isEnabled).hasValue(false);
    }

    @Test
    @SneakyThrows
    void enable_UserNotFound_ReturnError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.changeUserStatus(TestUtils.NOT_FOUND, false)
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));
        ResultActionErrorsUtil.checkNotFoundFields(resultActions,
                UserNotFoundException.MESSAGE_WITH_ID.formatted(TestUtils.NOT_FOUND),
                MockMvcUtils.USER_ID_URL.formatted(TestUtils.NOT_FOUND));
    }
}