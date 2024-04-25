package ru.veselov.restaurantvoting.extension.argproviders;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class BadUserDtoWithoutNullPasswordArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.concat(BadUserDtoWithPasswordArgumentsProvider.argumentsWithBadNamesAndEmails(),
                BadUserDtoWithPasswordArgumentsProvider.argumentsWithBadPasswordsExceptNull());
    }
}
