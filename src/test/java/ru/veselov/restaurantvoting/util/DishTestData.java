package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.model.Dish;

import java.util.List;

@UtilityClass
public class DishTestData {

    public static final int UNAGI_ID = TestUtils.START_SEQ + 9;

    public static final int PHILADELPHIA_ID = TestUtils.START_SEQ + 10;

    public static final int TASTY_ROLL_ID = TestUtils.START_SEQ + 11;

    public static final int PIZZA_ARRIVA_ID = TestUtils.START_SEQ + 12;

    public static final int DIABLO_PIZZA_ID = TestUtils.START_SEQ + 13;

    public static final int MARGARITA_PIZZA_ID = TestUtils.START_SEQ + 14;

    public static final int DOUBLE_BURGER_ID = TestUtils.START_SEQ + 15;

    public static final int TRIPLE_BURGER_ID = TestUtils.START_SEQ + 16;

    public static final int FRIES_POTATO_ID = TestUtils.START_SEQ + 17;

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "menu");

    public static final MatcherFactory.Matcher<DishDto> DISH_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishDto.class);

    public static Dish unagi = new Dish(UNAGI_ID, "Unagi", 40000);

    public static Dish philadelphia = new Dish(PHILADELPHIA_ID, "Philadelphia", 30000);

    public static Dish tastyRoll = new Dish(TASTY_ROLL_ID, "TastyRoll", 55000);

    public static List<Dish> sushiDishes = List.of(philadelphia, tastyRoll, unagi);

    public static DishDto philadelphiaDto = new DishDto(PHILADELPHIA_ID, "Philadelphia", 30000);

    public static DishDto philadelphiaDtoForConflict = new DishDto(null, "Philadelphia", 30000);

    public static DishDto philadelphiaDtoForConflictWithId = new DishDto(DishTestData.UNAGI_ID, "Philadelphia", 30000);

    public static DishDto tastyRollDto = new DishDto(TASTY_ROLL_ID, "TastyRoll", 55000);

    public static DishDto unagiDto = new DishDto(UNAGI_ID, "Unagi", 40000);

    public static List<DishDto> sushiDishesDtos = List.of(philadelphiaDto, tastyRollDto, unagiDto);

    public static DishDto pizzaArrivaDto = new DishDto(PIZZA_ARRIVA_ID, "Pizza Arriva", 60000);

    public static DishDto diabloPizzaDto = new DishDto(DIABLO_PIZZA_ID, "Diablo Pizza", 85000);

    public static DishDto margaritaDto = new DishDto(MARGARITA_PIZZA_ID, "Margarita", 55000);

    public static List<DishDto> pizzaDishesDtos = List.of(diabloPizzaDto, margaritaDto, pizzaArrivaDto);

    public static DishDto doubleBurgerDto = new DishDto(DOUBLE_BURGER_ID, "Double Burger", 65000);

    public static DishDto tripleBurgerDto = new DishDto(TRIPLE_BURGER_ID, "Triple Burger", 75000);

    public static DishDto friesPotatoDto = new DishDto(FRIES_POTATO_ID, "Fries Potato", 35000);

    public static List<DishDto> burgerDishesDtos = List.of(doubleBurgerDto, friesPotatoDto, tripleBurgerDto);

    public static DishDto newTastyDish = new DishDto(null, "veryTasty", 10000);

    public static DishDto savedNewTastyDish = new DishDto(TestUtils.LAST_SEQ, "veryTasty", 10000);

    public static DishDto dishToUpdate = new DishDto(TASTY_ROLL_ID, "TastyRoll", 10000);
}
