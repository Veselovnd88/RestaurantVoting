package ru.veselov.restaurantvoting.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

import java.util.List;
import java.util.Optional;

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
    void findAll_ReturnAllRestaurantsFromDB() {
        List<Restaurant> restaurants = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        Assertions.assertThat(restaurants).hasSize(RestaurantTestData.DB_COUNT);

        RestaurantTestData.RESTAURANT_MATCHER_NO_VOTES_NO_MENUS.assertMatch(restaurants,
                List.of(RestaurantTestData.burgerRestaurant, RestaurantTestData.pizzaRestaurant,
                        RestaurantTestData.sushiRestaurant));
    }

    @Test
    void findById_AllOk_ReturnSushiRestaurant() {
        Optional<Restaurant> restaurantOptional = repository.findById(100003);
        Assertions.assertThat(restaurantOptional).isPresent();
        Restaurant restaurant = restaurantOptional.get();
        RestaurantTestData.RESTAURANT_MATCHER_NO_VOTES_NO_MENUS.assertMatch(restaurant, RestaurantTestData.sushiRestaurant);
    }

    @Test
    void findById_AllOk_ReturnEmptyOptional() {
        Assertions.assertThat(repository.findById(9999)).isEmpty();
    }
}
