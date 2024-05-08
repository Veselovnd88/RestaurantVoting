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

    public static int USER1_ID = TestUtils.START_SEQ;

    public static int ADMIN_ID = TestUtils.START_SEQ + 1;

    public static int USER2_ID = TestUtils.START_SEQ + 2;

    public static int USER3_ID = TestUtils.START_SEQ + 21;

    public static int NEW_USER_ID = TestUtils.START_SEQ + 22;

    public static final String USER_1 = "User1";

    public static final String ADMIN = "Admin";

    public static final String USER_2 = "User2";

    public static final String USER_3 = "User3";

    public static final String USER1_EMAIL = "user@yandex.ru";

    public static final String ADMIN_EMAIL = "admin@gmail.com";

    public static final String USER2_EMAIL = "user2@gmail.com";

    public static final String USER3_EMAIL = "user3@gmail.com";

    public static final String NEW_USER = "newUser";

    public static final String USER2_UPD = "User2Upd";

    public static final String NEW_USER_EMAIL = "new@gmail.com";

    public static final String USER2_UPD_EMAIL = "user2upd@gmail.com";

    public static final String NEW_PASS = "newPass";

    public static final String ANOTHER = "another";

    public static final String SECRET = "secret";

    public static User user1 = new User(USER1_ID, USER_1, USER1_EMAIL, "password", true, List.of(Role.USER));

    public static User admin = new User(ADMIN_ID, ADMIN, ADMIN_EMAIL, "admin", true, List.of(Role.ADMIN, Role.USER));

    public static User user2 = new User(USER2_ID, USER_2, USER2_EMAIL, ANOTHER, true, List.of(Role.USER));

    public static User user3 = new User(USER3_ID, USER_3, USER3_EMAIL, ANOTHER, true, List.of(Role.USER));

    public static UserDto user1Dto = new UserDto(USER1_ID, USER_1, USER1_EMAIL, null);

    public static UserDto user2Dto = new UserDto(USER2_ID, USER_2, USER2_EMAIL, null);

    public static UserDto user3Dto = new UserDto(USER3_ID, USER_3, USER3_EMAIL, null);

    public static UserDto adminDto = new UserDto(ADMIN_ID, ADMIN, ADMIN_EMAIL, null);

    public static UserDto userToCreate = new UserDto(null, NEW_USER, NEW_USER_EMAIL, NEW_PASS);

    public static UserDto userToCreateWithConflict = new UserDto(null, NEW_USER, ADMIN_EMAIL, NEW_PASS);

    public static UserDto userToCreateWithId = new UserDto(1, NEW_USER, NEW_USER_EMAIL, NEW_PASS);

    public static UserDto userAfterCreate = new UserDto(NEW_USER_ID, NEW_USER, NEW_USER_EMAIL, null);

    public static UserDto user2ToUpdate = new UserDto(USER2_ID, USER2_UPD, USER2_UPD_EMAIL, null);

    public static UserDto user2ToUpdateForConflict = new UserDto(USER2_ID, USER2_UPD, ADMIN_EMAIL, null);

    public static UserDto user2DtoUpdated = new UserDto(USER2_ID, USER2_UPD, USER2_UPD_EMAIL, null);

    public static UserDto user2ToUpdateWithPass = new UserDto(USER2_ID, USER2_UPD, USER2_UPD_EMAIL, SECRET);

    public static User user2Updated = new User(USER2_ID, USER2_UPD, USER2_UPD_EMAIL, ANOTHER, true, List.of(Role.USER));

    public static User user2UpdatedWithPass = new User(USER2_ID, USER2_UPD, USER2_UPD_EMAIL, SECRET, true, List.of(Role.USER));

    public static User savedUser = new User(NEW_USER_ID, NEW_USER, NEW_USER_EMAIL, NEW_PASS, true, List.of(Role.USER));
}
