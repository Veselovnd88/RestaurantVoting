package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.model.Role;
import ru.veselov.restaurantvoting.model.User;

import java.util.List;

@UtilityClass
public class UserTestData {

    public static int USER1_ID = 100000;

    public static User user1 = new User(USER1_ID, "User1", "user@yandex.ru", "password", true, List.of(Role.USER));

    public static User admin = new User(100001, "Admin", "admin@gmail.com", "admin", true, List.of(Role.ADMIN, Role.USER));

    public static User user2 = new User(100002, "User2", "user2@gmail.com", "another", true, List.of(Role.USER));

    public static UserDto user1Dto = new UserDto(100000, "User1", "user@yandex.ru");

    public static UserDto user2Dto = new UserDto(100002, "User2", "user2@gmail.com");

    public static UserDto adminDto = new UserDto(100001, "Admin", "admin@gmail.com");
}