package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.model.Role;
import ru.veselov.restaurantvoting.model.User;

import java.util.List;

@UtilityClass
public class UserTestData {

    public static final MatcherFactory.Matcher<UserDto> USER_DTO_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(UserDto.class);

    public static int USER1_ID = 100000;

    public static int ADMIN_ID = USER1_ID + 1;

    public static int USER2_ID = USER1_ID + 2;

    public static int USER3_ID = 100021;

    public static int NEW_USER_ID = 100022;

    public static int NOT_FOUND = 200000;

    public static User user1 = new User(USER1_ID, "User1", "user@yandex.ru", "password", true, List.of(Role.USER));

    public static User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", true, List.of(Role.ADMIN, Role.USER));

    public static User user2 = new User(USER2_ID, "User2", "user2@gmail.com", "another", true, List.of(Role.USER));

    public static User user3 = new User(USER3_ID, "User3", "user3@gmail.com", "another", true, List.of(Role.USER));

    public static UserDto user1Dto = new UserDto(USER1_ID, "User1", "user@yandex.ru", null);

    public static UserDto user2Dto = new UserDto(USER2_ID, "User2", "user2@gmail.com", null);

    public static UserDto user3Dto = new UserDto(USER3_ID, "User3", "user3@gmail.com", null);

    public static UserDto adminDto = new UserDto(ADMIN_ID, "Admin", "admin@gmail.com", null);

    public static UserDto userToCreate = new UserDto(null, "newUser", "new@gmail.com", "newPass");

    public static UserDto userToCreateWithId = new UserDto(1, "newUser", "new@gmail.com", "newPass");

    public static UserDto userAfterCreate = new UserDto(NEW_USER_ID, "newUser", "new@gmail.com", null);

    public static UserDto user2ToUpdate = new UserDto(USER2_ID, "User2Upd", "user2Upd@gmail.com", null);

    public static UserDto userNotFoundToUpdate = new UserDto(NOT_FOUND, "UsernfUpd", "usernfUpd@gmail.com", null);

    public static UserDto user2DtoUpdated = new UserDto(USER2_ID, "User2Upd", "user2upd@gmail.com", null);

    public static UserDto user2ToUpdateWithPass = new UserDto(USER2_ID, "User2Upd", "user2Upd@gmail.com", "secret");

    public static User user2Updated = new User(USER2_ID, "User2Upd", "user2upd@gmail.com", "another", true, List.of(Role.USER));

    public static User user2UpdatedWithPass = new User(USER2_ID, "User2Upd", "user2upd@gmail.com", "secret", true, List.of(Role.USER));

    public static User savedUser = new User(NEW_USER_ID, "newUser", "new@gmail.com", "newPass", true, List.of(Role.USER));
}
