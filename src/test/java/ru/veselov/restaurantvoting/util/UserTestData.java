package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.model.Role;
import ru.veselov.restaurantvoting.model.User;

import java.util.List;

@UtilityClass
public class UserTestData {

    public static int USER1_ID = 100000;

    public static int ADMIN_ID = 100001;

    public static int USER2_ID = 100002;

    public static int USER3_ID = 100021;

    public static User user1 = new User(USER1_ID, "User1", "user@yandex.ru", "password", true, List.of(Role.USER));

    public static User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", true, List.of(Role.ADMIN, Role.USER));

    public static User user2 = new User(USER2_ID, "User2", "user2@gmail.com", "another", true, List.of(Role.USER));

    public static User user3 = new User(USER3_ID, "User3", "user3@gmail.com", "another", true, List.of(Role.USER));

    public static UserDto user1Dto = new UserDto(USER1_ID, "User1", "user@yandex.ru");

    public static UserDto user2Dto = new UserDto(USER2_ID, "User2", "user2@gmail.com");

    public static UserDto user3Dto = new UserDto(USER3_ID, "User3", "user3@gmail.com");

    public static UserDto adminDto = new UserDto(ADMIN_ID, "Admin", "admin@gmail.com");
}
