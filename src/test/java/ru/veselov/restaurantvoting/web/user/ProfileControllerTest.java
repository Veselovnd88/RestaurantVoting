package ru.veselov.restaurantvoting.web.user;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.model.User;
import ru.veselov.restaurantvoting.repository.UserRepository;
import ru.veselov.restaurantvoting.service.user.UserService;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.util.SecurityUtils;
import ru.veselov.restaurantvoting.util.UserTestData;
import ru.veselov.restaurantvoting.web.AbstractRestControllerTest;
import ru.veselov.restaurantvoting.web.GlobalExceptionHandler;

import java.util.Optional;

class ProfileControllerTest extends AbstractRestControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @SneakyThrows
    void register_AllOk_RegisterNewUser() {
        mockMvc.perform(MockMvcUtils.register(UserTestData.userToCreate))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(UserTestData.USER_DTO_MATCHER.contentJson(UserTestData.userAfterCreate));
    }

    @Test
    @SneakyThrows
    void register_EmailDuplicate_ReturnError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.register(UserTestData.userToCreateWithConflict)
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkConflictError(resultActions, GlobalExceptionHandler.USER_WITH_EMAIL_EXISTS,
                MockMvcUtils.REGISTER);
    }

    @Test
    @SneakyThrows
    void getProfile_AllOk_ReturnUserDto() {
        mockMvc.perform(MockMvcUtils.getUserProfile().with(SecurityUtils.userHttpBasic(UserTestData.user1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(UserTestData.USER_DTO_MATCHER.contentJson(UserTestData.user1Dto));
    }

    @Test
    @SneakyThrows
    void update_AllOK_UpdateUser() {
        mockMvc.perform(MockMvcUtils.updateUserProfile(UserTestData.user2ToUpdate)
                        .with(SecurityUtils.userHttpBasic(UserTestData.user2)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThat(userService.getById(UserTestData.USER2_ID)).isEqualTo(UserTestData.user2DtoUpdated);
    }

    @Test
    @SneakyThrows
    void update_AllOKWithChangingPassword_UpdateUser() {
        mockMvc.perform(MockMvcUtils.updateUserProfile(UserTestData.user2ToUpdateWithPass)
                        .with(SecurityUtils.userHttpBasic(UserTestData.user2)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThat(userService.getById(UserTestData.USER2_ID)).isEqualTo(UserTestData.user2DtoUpdated);
        Optional<User> byId = userRepository.findById(UserTestData.USER2_ID);
        Assertions.assertThat(byId).isPresent();
        passwordEncoder.matches(byId.get().getPassword(),
                passwordEncoder.encode(UserTestData.user2ToUpdateWithPass.password()));
    }

    @Test
    @SneakyThrows
    void update_EmailDuplicate_ReturnError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.updateUserProfile(UserTestData.user2ToUpdateForConflict)
                .with(SecurityUtils.userHttpBasic(UserTestData.user2)));

        ResultActionErrorsUtil.checkConflictError(resultActions, GlobalExceptionHandler.USER_WITH_EMAIL_EXISTS,
                ProfileController.REST_URL);
    }

    @Test
    @SneakyThrows
    void deleteById_AllOk_DeleteUser() {
        mockMvc.perform(MockMvcUtils.deleteUser().with(SecurityUtils.userHttpBasic(UserTestData.user2)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThat(userRepository.findById(UserTestData.USER2_ID)).isEmpty();
    }
}
