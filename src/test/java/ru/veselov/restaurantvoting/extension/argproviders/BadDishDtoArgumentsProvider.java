package ru.veselov.restaurantvoting.extension.argproviders;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import ru.veselov.restaurantvoting.dto.DishDto;

import java.util.stream.Stream;

public class BadDishDtoArgumentsProvider implements ArgumentsProvider {

    public static final String NAME = "name";

    public static final String PRICE = "price";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        DishDto blankName = new DishDto(null, "", 1000);
        DishDto nullName = new DishDto(null, null, 1000);
        DishDto shortName = new DishDto(null, "a", 1000);
        DishDto longName = new DishDto(null, "a".repeat(256), 1000);
        DishDto smallPrice = new DishDto(null, "dishName", 999);
        DishDto bigPrice = new DishDto(null, "dishName", 1000001);
        return Stream.of(
                Arguments.of(blankName, NAME),
                Arguments.of(nullName, NAME),
                Arguments.of(shortName, NAME),
                Arguments.of(longName, NAME),
                Arguments.of(smallPrice, PRICE),
                Arguments.of(bigPrice, PRICE)
        );
    }
}
