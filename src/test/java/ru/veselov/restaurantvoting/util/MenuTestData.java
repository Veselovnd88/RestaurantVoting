package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class MenuTestData {

    public static Menu sushiRestaurantMenu = new Menu(LocalDate.of(2024, 3, 6), List.of(
            DishTestData.unagi, DishTestData.philadelphia, DishTestData.tastyRoll), RestaurantTestData.sushiRestaurant);
}
