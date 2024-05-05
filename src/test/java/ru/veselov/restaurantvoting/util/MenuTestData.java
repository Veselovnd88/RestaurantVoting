package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.model.Dish;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;

@UtilityClass
public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            Menu.class, "restaurant", "votes", "dishes");

    public static final MatcherFactory.Matcher<MenuDto> MENU_DTO_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(MenuDto.class);

    public static final LocalDate MENU_DATE = LocalDate.of(2024, 3, 6);

    public static final int SUSHI_MENU_ID = 100006;

    public static final int PIZZA_MENU_ID = SUSHI_MENU_ID + 1;

    public static final int NOT_FOUND_MENU = 200006;

    public static final int BURGER_MENU_ID = SUSHI_MENU_ID + 2;

    public static MenuDto sushiRestaurantMenuDto = new MenuDto(SUSHI_MENU_ID, MENU_DATE, DishTestData.sushiDishesDtos, null);

    public static MenuDto pizzaRestaurantMenuDto = new MenuDto(PIZZA_MENU_ID, MENU_DATE, DishTestData.pizzaDishesDtos, null);

    public static MenuDto burgerRestaurantMenuDto = new MenuDto(BURGER_MENU_ID, MENU_DATE, DishTestData.burgerDishesDtos, null);

    public static Menu pizzaRestaurantMenu = new Menu(PIZZA_MENU_ID, MENU_DATE, RestaurantTestData.pizzaRestaurant,
            DishTestData.diabloPizza, DishTestData.margarita, DishTestData.pizzaArriva);

    public static Menu burgerRestaurantMenu = new Menu(BURGER_MENU_ID, MENU_DATE, RestaurantTestData.burgerRestaurant,
            DishTestData.doubleBurger, DishTestData.friesPotato, DishTestData.tripleBurger);

    public static Menu menuToCreateWithoutId = new Menu(null, MENU_DATE.plusDays(1), null, null, null);

    public static MenuDto createdMenuDto = new MenuDto(100022, MENU_DATE.plusDays(1),
            List.of(DishTestData.savedWithMenuNewTastyDish, DishTestData.savedWithMenuNewTastyDish2), null);

    public static InputMenuDto menuDtoToCreate = new InputMenuDto(null, MENU_DATE.plusDays(1),
            List.of(DishTestData.newTastyDish, DishTestData.newTastyDish2));

    public static InputMenuDto menuDtoToCreateSimilarDishes = new InputMenuDto(null, MENU_DATE.plusDays(1),
            List.of(DishTestData.newTastyDish, DishTestData.newTastyDish));

    public static InputMenuDto menuDtoToCreateForConflict = new InputMenuDto(null, MENU_DATE,
            List.of(DishTestData.newTastyDish, DishTestData.newTastyDish2));

    public static InputMenuDto menuDtoToUpdate = new InputMenuDto(SUSHI_MENU_ID, MENU_DATE,
            List.of(DishTestData.newTastyDish, DishTestData.newTastyDish2));

    public static InputMenuDto menuDtoToUpdateForConflict =
            new InputMenuDto(null, MENU_DATE, List.of(DishTestData.savedNewTastyDish));

    public static InputMenuDto menuDtoToUpdateWithSimilarDishes = new InputMenuDto(SUSHI_MENU_ID, MENU_DATE,
            List.of(DishTestData.newTastyDish, DishTestData.newTastyDish));

    public static InputMenuDto menuDtoToUpdateWithChangedDish = new InputMenuDto(SUSHI_MENU_ID, MENU_DATE,
            List.of(DishTestData.philadelphiaDto, DishTestData.tastyRollDto, DishTestData.changedUnagiDto));

    public static MenuDto menuDtoToUpdateWithChangedDishAfterUpdate = new MenuDto(SUSHI_MENU_ID, MENU_DATE,
            List.of(DishTestData.philadelphiaDto, DishTestData.tastyRollDto, DishTestData.changedUnagiDto), null);

    public static Menu getGetSushiRestaurantMenu() {
        return new Menu(100006, MenuTestData.MENU_DATE, new Restaurant(RestaurantTestData.SUSHI_ID, "SushiWok"),
                new LinkedHashSet<>(List.of(new Dish(100010, "Philadelphia", 30000),
                        new Dish(100011, "TastyRoll", 55000),
                        new Dish(100009, "Unagi", 40000))));
    }

    public static Menu getSushiRestaurantMenuWithVotes() {
        Menu menu = new Menu(100006, MENU_DATE, RestaurantTestData.sushiRestaurant,
                new LinkedHashSet<>(DishTestData.sushiDishes));
        Vote user1VoteSushi = new Vote(100018, VoteTestData.VOTED_AT_DATE, UserTestData.user1, menu);
        Vote adminVoteSushi = new Vote(100019, VoteTestData.VOTED_AT_DATE, UserTestData.admin, menu);
        Vote user2VoteSushi = new Vote(100020, VoteTestData.VOTED_AT_DATE, UserTestData.user2, menu);
        menu.setVotes(new LinkedHashSet<>(List.of(user1VoteSushi, adminVoteSushi, user2VoteSushi)));
        return menu;
    }
}
