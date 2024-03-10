package ru.veselov.restaurantvoting.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
@Sql(scripts = {"classpath:db/init.sql", "classpath:db/populateDbTest.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository repository;

    @BeforeEach
    void setUp() {
        RestaurantTestData.sushiRestaurant.setMenus(List.of(MenuTestData.sushiRestaurantMenu));
        RestaurantTestData.pizzaRestaurant.setMenus(List.of(MenuTestData.pizzaRestaurantMenu));
        RestaurantTestData.burgerRestaurant.setMenus(List.of(MenuTestData.burgerRestaurantMenu));
    }

    @Test
    void findAllWithMenus_ReturnAllRestaurantsWithAllMenus() {
        List<Restaurant> allWithMenus = repository.findAllWithMenuByDate(LocalDate.of(2024, 3, 6));

        Assertions.assertThat(allWithMenus).hasSize(RestaurantTestData.DB_COUNT);

        RestaurantTestData.RESTAURANT_MATCHER_NO_VOTES.assertMatch(allWithMenus, List.of(
                RestaurantTestData.burgerRestaurant, RestaurantTestData.pizzaRestaurant, RestaurantTestData.sushiRestaurant
        ));
        List<Menu> menus = allWithMenus.stream().flatMap(r -> r.getMenus().stream()).toList();
        MenuTestData.MENU_MATCHER.assertMatch(menus,
                List.of(MenuTestData.burgerRestaurantMenu, MenuTestData.pizzaRestaurantMenu, MenuTestData.sushiRestaurantMenu)
        );
        DishTestData.DISH_MATCHER.assertMatch(menus.stream().flatMap(menu -> menu.getDishes().stream()).collect(Collectors.toList()),
                List.of(DishTestData.doubleBurger, DishTestData.friesPotato, DishTestData.tripleBurger,
                        DishTestData.diabloPizza, DishTestData.margarita, DishTestData.pizzaArriva,
                        DishTestData.philadelphia, DishTestData.tastyRoll, DishTestData.unagi));
    }
}
