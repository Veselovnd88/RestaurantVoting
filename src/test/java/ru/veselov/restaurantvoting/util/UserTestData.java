package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.model.Role;
import ru.veselov.restaurantvoting.model.User;

import java.util.List;

@UtilityClass
public class UserTestData {

    public static int USER1_ID = 100000;

    public static int ADMIN_ID = USER1_ID + 1;

    public static int USER2_ID = USER1_ID + 2;

    public static int USER3_ID = USER1_ID + 3;

    public static int NEW_USER_ID = USER1_ID + 4;

    public static User user1 = new User(USER1_ID, "User1", "user@yandex.ru", "password", true, List.of(Role.USER));

    public static User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", true, List.of(Role.ADMIN, Role.USER));

    public static User user2 = new User(USER2_ID, "User2", "user2@gmail.com", "another", true, List.of(Role.USER));

    public static User user3 = new User(USER3_ID, "User3", "user3@gmail.com", "another", true, List.of(Role.USER));

    public static UserDto user1Dto = new UserDto(USER1_ID, "User1", "user@yandex.ru", null);

    public static UserDto user2Dto = new UserDto(USER2_ID, "User2", "user2@gmail.com", null);

    public static UserDto user3Dto = new UserDto(USER3_ID, "User3", "user3@gmail.com", null);

    public static UserDto adminDto = new UserDto(ADMIN_ID, "Admin", "admin@gmail.com", null);

    public static UserDto userToCreate = new UserDto(null, "newUser", "new@gmail.com", "new");

    public static UserDto user2ToUpdate = new UserDto(USER2_ID, "User2Upd", "user2Upd@gmail.com", null);

    public static UserDto userToUpdateWithPass = new UserDto(USER2_ID, "User2Upd", "user2Upd@gmail.com", "secret");

    public static User user2Updated = new User(USER2_ID, "User2Upd", "user2upd@gmail.com", "another", true, List.of(Role.USER));

    public static User user2UpdatedWithPass = new User(USER2_ID, "User2Upd", "user2upd@gmail.com", "secret", true, List.of(Role.USER));

    public static User savedUser = new User(NEW_USER_ID, "newUser", "new@gmail.com", "new", true, List.of(Role.USER));
}
