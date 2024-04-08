package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.model.Dish;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;

@UtilityClass
public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            Menu.class, "restaurant", "votes", "dishes");

    public static final MatcherFactory.Matcher<MenuDto> MENU_DTO_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(MenuDto.class);

    public static final LocalDate ADDED_DATE = LocalDate.of(2024, 3, 6);

    public static final int SUSHI_MENU_ID = 100006;

    public static final int BURGER_MENU_ID = 100008;

    public static Menu sushiRestaurantMenu = new Menu(100006, ADDED_DATE, RestaurantTestData.sushiRestaurant,
            new LinkedHashSet<>(DishTestData.sushiDishes));

    public static Menu sushiRestaurantMenuWithVotes = new Menu(100006, ADDED_DATE, RestaurantTestData.sushiRestaurant,
            new LinkedHashSet<>(DishTestData.sushiDishes), new LinkedHashSet<>(VoteTestData.sushiVotes));

    public static MenuDto sushiRestaurantMenuDtoWithVotes = new MenuDto(100006, ADDED_DATE, DishTestData.sushiDishesDtos, VoteTestData.sushiVotesDto);

    public static MenuDto sushiRestaurantMenuDto = new MenuDto(100006, ADDED_DATE, DishTestData.sushiDishesDtos, null);

    public static Menu pizzaRestaurantMenu = new Menu(100007, ADDED_DATE, RestaurantTestData.pizzaRestaurant,
            DishTestData.diabloPizza, DishTestData.margarita, DishTestData.pizzaArriva);

    public static Menu burgerRestaurantMenu = new Menu(BURGER_MENU_ID, ADDED_DATE, RestaurantTestData.burgerRestaurant,
            DishTestData.doubleBurger, DishTestData.friesPotato, DishTestData.tripleBurger);

    public static Menu menuToCreateWithoutId = new Menu(null, ADDED_DATE.plusDays(1), null, null, null);

    public static MenuDto createdMenuDto = new MenuDto(100022, ADDED_DATE.plusDays(1),
            List.of(DishTestData.savedWithMenuNewTastyDish), null);

    public static NewMenuDto menuDtoToCreate = new NewMenuDto(null, ADDED_DATE.plusDays(1),
            List.of(DishTestData.newTastyDish));

    public static NewMenuDto menuDtoToUpdate = new NewMenuDto(SUSHI_MENU_ID, ADDED_DATE, List.of(DishTestData.newTastyDish));

    public static NewMenuDto menuDtoToUpdateWithChangedDish = new NewMenuDto(SUSHI_MENU_ID, ADDED_DATE,
            List.of(DishTestData.philadelphiaDto, DishTestData.tastyRollDto, DishTestData.changedUnagiDto));

    public static MenuDto menuDtoToUpdateWithChangedDishAfterUpdate = new MenuDto(SUSHI_MENU_ID, ADDED_DATE,
            List.of(DishTestData.philadelphiaDto, DishTestData.tastyRollDto, DishTestData.changedUnagiDto), VoteTestData.sushiVotesDto);

    public static Menu getSushiRestaurantMenu() {
        return new Menu(100006, MenuTestData.ADDED_DATE, new Restaurant(RestaurantTestData.SUSHI_ID, "SushiWok"),
                new LinkedHashSet<>(List.of(new Dish(100010, "Philadelphia", 30000),
                        new Dish(100011, "TastyRoll", 55000),
                        new Dish(100009, "Unagi", 40000))));
    }
}
