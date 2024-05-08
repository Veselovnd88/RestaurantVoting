package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.model.Dish;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;

@UtilityClass
public class MenuTestData {

    public static final LocalDate MENU_DATE = LocalDate.of(2024, 3, 6);

    public static final int SUSHI_MENU_ID = TestUtils.START_SEQ + 6;

    public static final int PIZZA_MENU_ID = TestUtils.START_SEQ + 7;

    public static final int BURGER_MENU_ID = TestUtils.START_SEQ + 8;

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            Menu.class, "restaurant", "dishes");

    public static final MatcherFactory.Matcher<MenuDto> MENU_DTO_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(MenuDto.class);

    public static MenuDto sushiRestaurantMenuDto = new MenuDto(SUSHI_MENU_ID, MENU_DATE, DishTestData.sushiDishesDtos, null);

    public static MenuDto pizzaRestaurantMenuDto = new MenuDto(PIZZA_MENU_ID, MENU_DATE, DishTestData.pizzaDishesDtos, null);

    public static MenuDto burgerRestaurantMenuDto = new MenuDto(BURGER_MENU_ID, MENU_DATE, DishTestData.burgerDishesDtos, null);

    public static MenuDto createdMenuDto = new MenuDto(100022, MENU_DATE.plusDays(1), null, null);

    public static InputMenuDto menuDtoToUpdate = new InputMenuDto(SUSHI_MENU_ID, MENU_DATE.plusDays(5));

    public static Menu getGetSushiRestaurantMenu() {
        return new Menu(100006, MenuTestData.MENU_DATE, new Restaurant(RestaurantTestData.SUSHI_ID, "SushiWok"),
                new LinkedHashSet<>(List.of(new Dish(100010, "Philadelphia", 30000),
                        new Dish(100011, "TastyRoll", 55000),
                        new Dish(100009, "Unagi", 40000))));
    }
}
