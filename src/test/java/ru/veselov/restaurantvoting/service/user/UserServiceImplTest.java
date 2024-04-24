package ru.veselov.restaurantvoting.service.user;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.exception.UserNotFoundException;
import ru.veselov.restaurantvoting.mapper.UserMapper;
import ru.veselov.restaurantvoting.mapper.UserMapperImpl;
import ru.veselov.restaurantvoting.model.Role;
import ru.veselov.restaurantvoting.model.User;
import ru.veselov.restaurantvoting.repository.UserRepository;
import ru.veselov.restaurantvoting.util.UserTestData;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "mapper", new UserMapperImpl(), UserMapper.class);
    }

    @Test
    void create_AllOk_SaveUser() {
        Mockito.when(repository.save(userCaptor.capture())).thenReturn(UserTestData.savedUser);

        UserDto userDto = userService.create(UserTestData.userToCreate);

        User captured = userCaptor.getValue();
        Assertions.assertThat(captured.getRoles()).hasSize(1).contains(Role.USER);
        Assertions.assertThat(userDto).isNotNull()
                .extracting(UserDto::email, UserDto::id, UserDto::name, UserDto::password)
                .containsExactly(UserTestData.savedUser.getEmail(), UserTestData.savedUser.getId(),
                        UserTestData.savedUser.getName(), null);

        Mockito.verify(repository).save(captured);
        Mockito.verify(passwordEncoder).encode(stringCaptor.capture());
        Assertions.assertThat(stringCaptor.getValue()).isEqualTo(UserTestData.userToCreate.password());
    }

    @Test
    void update_AllOkPassWasNotChanged_UpdateUser() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(UserTestData.user2));
        Mockito.when(repository.save(userCaptor.capture())).thenReturn(UserTestData.user2Updated);

        userService.update(UserTestData.user2ToUpdate);

        User captured = userCaptor.getValue();
        Assertions.assertThat(captured).extracting(User::getEmail, User::getName, User::getPassword)
                .containsExactly(UserTestData.user2ToUpdate.email().toLowerCase(),
                        UserTestData.user2ToUpdate.name(),
                        UserTestData.user2.getPassword());
        Mockito.verifyNoInteractions(passwordEncoder);
        Mockito.verify(repository).save(captured);
    }

    @Test
    void update_AllOkPassAlsoChanged_UpdateUser() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(UserTestData.user2));
        Mockito.when(repository.save(userCaptor.capture())).thenReturn(UserTestData.user2UpdatedWithPass);

        userService.update(UserTestData.userToUpdateWithPass);

        User captured = userCaptor.getValue();
        Assertions.assertThat(captured).extracting(User::getEmail, User::getName)
                .containsExactly(UserTestData.user2ToUpdate.email().toLowerCase(), UserTestData.userToUpdateWithPass.name());
        Mockito.verify(passwordEncoder).encode(stringCaptor.capture());
        Mockito.verify(repository).save(captured);
        Assertions.assertThat(stringCaptor.getValue()).isEqualTo(UserTestData.userToUpdateWithPass.password());
    }

    @Test
    void update_UserNotFound_ThrowException() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(
                        () -> userService.update(UserTestData.user2ToUpdate))
                .withMessage(UserNotFoundException.MESSAGE_WITH_ID.formatted(UserTestData.USER2_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void delete_AllOk_Delete() {
        userService.deleteById(UserTestData.USER2_ID);

        Mockito.verify(repository).deleteById(UserTestData.USER2_ID);
    }

    @Test
    void enable_AllOk_ChangeStatus() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(UserTestData.user2));

        userService.enable(UserTestData.USER2_ID, false);

        Mockito.verify(repository).save(userCaptor.capture());
        Assertions.assertThat(userCaptor.getValue().isEnabled()).isFalse();
    }

    @Test
    void enable_UserNotFound_ThrowException() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.enable(UserTestData.USER2_ID, false))
                .withMessage(UserNotFoundException.MESSAGE_WITH_ID.formatted(UserTestData.USER2_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getById_AllOk_ReturnUserDto() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(UserTestData.user2));

        UserDto byId = userService.getById(UserTestData.USER2_ID);

        Assertions.assertThat(byId).isEqualTo(UserTestData.user2Dto);
    }

    @Test
    void getById_UserNotFound_ThrowException() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.getById(UserTestData.USER2_ID))
                .withMessage(UserNotFoundException.MESSAGE_WITH_ID.formatted(UserTestData.USER2_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getByEmail_AllOK_ReturnUserDto() {
        Mockito.when(repository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(UserTestData.user2));

        Assertions.assertThat(userService.getByEmail(UserTestData.user2Dto.email()))
                .isEqualTo(UserTestData.user2Dto);
    }

    @Test
    void getByEmail_UserNotFound_ThrowException() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        String email = UserTestData.user2.getEmail();

        Assertions.assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.getByEmail(email))
                .withMessage(UserNotFoundException.MESSAGE_WITH_EMAIL.formatted(email))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getAll_AllOk_ReturnListUserDtos() {
        Mockito.when(repository.findAll()).thenReturn(List.of(UserTestData.user2));

        Assertions.assertThat(userService.getAll()).isEqualTo(List.of(UserTestData.user2Dto));
    }

    @Test
    void getAll_NoUser_ReturnEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertThat(userService.getAll()).isEmpty();
    }
}
