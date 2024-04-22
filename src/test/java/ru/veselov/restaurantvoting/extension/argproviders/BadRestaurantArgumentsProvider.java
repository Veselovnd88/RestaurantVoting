package ru.veselov.restaurantvoting.extension.argproviders;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import ru.veselov.restaurantvoting.dto.InputRestaurantDto;

import java.util.stream.Stream;

public class BadRestaurantArgumentsProvider implements ArgumentsProvider {
    public static final String NAME = "name";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        InputRestaurantDto blankName = new InputRestaurantDto("");
        InputRestaurantDto nullName = new InputRestaurantDto(null);
        InputRestaurantDto shortName = new InputRestaurantDto("f".repeat(4));
        InputRestaurantDto longName = new InputRestaurantDto("f".repeat(126));
        return Stream.of(Arguments.of(blankName, NAME),
                Arguments.of(nullName, NAME),
                Arguments.of(shortName, NAME),
                Arguments.of(longName, NAME));
    }
}
