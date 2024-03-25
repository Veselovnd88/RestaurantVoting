package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.model.Dish;

import java.util.List;

@UtilityClass
public class DishTestData {

    public static MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("menus");

    public static Dish unagi = new Dish(100009, "Unagi", 40000);

    public static Dish philadelphia = new Dish(100010, "Philadelphia", 30000);

    public static Dish tastyRoll = new Dish(100011, "TastyRoll", 55000);

    public static List<Dish> sushiDishes = List.of(philadelphia, tastyRoll, unagi);

    public static Dish pizzaArriva = new Dish(100012, "Pizza Arriva", 60000);

    public static Dish diabloPizza = new Dish(100013, "Diablo Pizza", 85000);

    public static Dish margarita = new Dish(100014, "Margarita", 55000);

    public static Dish doubleBurger = new Dish(100015, "Double Burger", 65000);

    public static Dish tripleBurger = new Dish(100016, "Triple Burger", 75000);

    public static Dish friesPotato = new Dish(100017, "Fries Potato", 35000);

    public static DishDto philadelphiaDto = new DishDto(100010, "Philadelphia", 30000);

    public static DishDto tastyRollDto = new DishDto(100011, "TastyRoll", 55000);

    public static DishDto unagiDto = new DishDto(100009, "Unagi", 40000);

    public static List<DishDto> sushiDishesDtos = List.of(philadelphiaDto, tastyRollDto, unagiDto);
}
