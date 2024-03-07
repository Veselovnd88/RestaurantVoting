package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.model.Dish;

@UtilityClass
public class DishTestData {

    public static Dish unagi = new Dish(100006, "Unagi", 40000, MenuTestData.sushiRestaurantMenu);

    public static Dish philadelphia = new Dish(100007, "Philadelphia", 30000, MenuTestData.sushiRestaurantMenu);

    public static Dish tastyRoll = new Dish(100007, "TastyRoll", 55000, MenuTestData.sushiRestaurantMenu);

}
