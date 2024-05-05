package ru.veselov.restaurantvoting.extension.argproviders;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class BadInputMenuDtoArgumentsProvider implements ArgumentsProvider {

    public static final String DISHES = "dishes";
    public static final String ADDED_AT = "date";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        InputMenuDto nullDate = new InputMenuDto(null, null, List.of(DishTestData.newTastyDish, DishTestData.philadelphiaDto));
        InputMenuDto similarDishes = new InputMenuDto(null, MenuTestData.ADDED_DATE,
                List.of(DishTestData.newTastyDish, DishTestData.newTastyDish));
        InputMenuDto oneDish = new InputMenuDto(null, MenuTestData.ADDED_DATE, List.of(DishTestData.newTastyDish));
        InputMenuDto noDishes = new InputMenuDto(null, MenuTestData.ADDED_DATE, Collections.emptyList());
        InputMenuDto sixDishes = new InputMenuDto(null, MenuTestData.ADDED_DATE,
                List.of(DishTestData.newTastyDish, DishTestData.philadelphiaDto, DishTestData.diabloPizzaDto,
                        DishTestData.doubleBurgerDto, DishTestData.friesPotatoDto, DishTestData.pizzaArrivaDto));

        return Stream.of(
                Arguments.of(nullDate, ADDED_AT),
                Arguments.of(similarDishes, DISHES),
                Arguments.of(oneDish, DISHES),
                Arguments.of(noDishes, DISHES),
                Arguments.of(sixDishes, DISHES)
        );
    }
}
