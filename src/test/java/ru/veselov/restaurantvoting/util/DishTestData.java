package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.model.Dish;

import java.util.List;

@UtilityClass
public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "menu");

    public static final MatcherFactory.Matcher<DishDto> DISH_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishDto.class);

    public static final int TASTY_ROLL_ID = 100011;

    public static final int UNAGI_ID = 100009;

    public static final int PHILADELPHIA_ID = 100010;

    public static Dish unagi = new Dish(UNAGI_ID, "Unagi", 40000);

    public static Dish philadelphia = new Dish(PHILADELPHIA_ID, "Philadelphia", 30000);

    public static Dish tastyRoll = new Dish(TASTY_ROLL_ID, "TastyRoll", 55000);

    public static List<Dish> sushiDishes = List.of(philadelphia, tastyRoll, unagi);

    public static Dish pizzaArriva = new Dish(100012, "Pizza Arriva", 60000);

    public static DishDto pizzaArrivaDto = new DishDto(100012, "Pizza Arriva", 60000);

    public static Dish diabloPizza = new Dish(100013, "Diablo Pizza", 85000);

    public static DishDto diabloPizzaDto = new DishDto(100013, "Diablo Pizza", 85000);

    public static Dish margarita = new Dish(100014, "Margarita", 55000);

    public static DishDto margaritaDto = new DishDto(100014, "Margarita", 55000);

    public static Dish doubleBurger = new Dish(100015, "Double Burger", 65000);

    public static DishDto doubleBurgerDto = new DishDto(100015, "Double Burger", 65000);

    public static Dish tripleBurger = new Dish(100016, "Triple Burger", 75000);

    public static DishDto tripleBurgerDto = new DishDto(100016, "Triple Burger", 75000);

    public static Dish friesPotato = new Dish(100017, "Fries Potato", 35000);

    public static DishDto friesPotatoDto = new DishDto(100017, "Fries Potato", 35000);

    public static DishDto philadelphiaDto = new DishDto(100010, "Philadelphia", 30000);

    public static DishDto philadelphiaDtoForConflict = new DishDto(null, "Philadelphia", 30000);

    public static DishDto tastyRollDto = new DishDto(TASTY_ROLL_ID, "TastyRoll", 55000);

    public static DishDto unagiDto = new DishDto(UNAGI_ID, "Unagi", 40000);

    public static List<DishDto> sushiDishesDtos = List.of(philadelphiaDto, tastyRollDto, unagiDto);

    public static DishDto newTastyDish = new DishDto(null, "veryTasty", 10000);

    public static DishDto newTastyDish2 = new DishDto(null, "veryTasty2", 10000);

    public static Dish tastyDishEntity = new Dish(null, "veryTasty", 10000);

    public static DishDto savedNewTastyDish = new DishDto(100022, "veryTasty", 10000);

    public static DishDto savedWithMenuNewTastyDish = new DishDto(100023, "veryTasty", 10000);

    public static DishDto savedWithMenuNewTastyDish2 = new DishDto(100024, "veryTasty", 10000);

    public static DishDto dishToUpdate = new DishDto(TASTY_ROLL_ID, "TastyRoll", 10000);

    public static List<DishDto> allDishes = List.of(diabloPizzaDto, doubleBurgerDto, friesPotatoDto, margaritaDto,
            philadelphiaDto, pizzaArrivaDto, tastyRollDto, tripleBurgerDto, unagiDto);

    public static DishDto changedUnagiDto = new DishDto(UNAGI_ID, "UnagiUpd", 45000);
}
