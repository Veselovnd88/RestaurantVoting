package ru.veselov.restaurantvoting.extension.argproviders;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import ru.veselov.restaurantvoting.dto.UserDto;

import java.util.stream.Stream;

public class BadUserDtoWithPasswordArgumentsProvider implements ArgumentsProvider {

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.concat(argumentsWithBadNamesAndEmails(), Stream.concat(argumentsWithBadPasswordsExceptNull(),
                argumentsWithNullPassword()));
    }

    public static Stream<Arguments> argumentsWithBadNamesAndEmails() {
        UserDto blankName = new UserDto(null, "", "user@yandex.ru", "password");
        UserDto nullName = new UserDto(null, null, "user@yandex.ru", "password");
        UserDto shortName = new UserDto(null, "1", "user@yandex.ru", "password");
        UserDto longName = new UserDto(null, "1".repeat(101), "user@yandex.ru", "password");
        UserDto blankEmail = new UserDto(null, "name", "", "password");
        UserDto nullEmail = new UserDto(null, "name", null, "password");
        UserDto badEmail = new UserDto(null, "name", "notEmail", "password");
        UserDto longEmail = new UserDto(null, "name", "f".repeat(100).concat("@gmail.com"), "password");
        return Stream.of(
                Arguments.of(blankName, NAME),
                Arguments.of(nullName, NAME),
                Arguments.of(shortName, NAME),
                Arguments.of(longName, NAME),
                Arguments.of(blankEmail, EMAIL),
                Arguments.of(nullEmail, EMAIL),
                Arguments.of(badEmail, EMAIL),
                Arguments.of(longEmail, EMAIL));
    }

    public static Stream<Arguments> argumentsWithBadPasswordsExceptNull() {
        UserDto shortPassword = new UserDto(null, "name", "user@gmail.com", "aa");
        UserDto longPassword = new UserDto(null, "name", "user@gmail.com", "a".repeat(33));
        UserDto blankPassword = new UserDto(null, "name", "user@gmail.com", "");
        return Stream.of(
                Arguments.of(shortPassword, PASSWORD),
                Arguments.of(longPassword, PASSWORD),
                Arguments.of(blankPassword, PASSWORD)
        );
    }

    public static Stream<Arguments> argumentsWithNullPassword() {
        UserDto nullPassword = new UserDto(null, "name", "user@gmail.com", null);
        return Stream.of(Arguments.of(nullPassword, PASSWORD));
    }
}
