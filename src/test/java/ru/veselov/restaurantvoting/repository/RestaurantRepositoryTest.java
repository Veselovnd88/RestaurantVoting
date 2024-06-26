package ru.veselov.restaurantvoting.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.service.restaurant.RestaurantServiceImpl;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.TestUtils;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Sql(scripts = {"classpath:db/init.sql", "classpath:db/populateDbTest.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository repository;

    @Test
    void findAll_ReturnAllRestaurantsFromDB() {
        List<Restaurant> restaurants = repository.findAll(RestaurantServiceImpl.SORT_BY_NAME);

        Assertions.assertThat(restaurants).hasSize(RestaurantTestData.DB_COUNT);

        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurants,
                List.of(RestaurantTestData.burgerRestaurant, RestaurantTestData.pizzaRestaurant,
                        RestaurantTestData.sushiRestaurant));
    }

    @Test
    void findById_AllOk_ReturnSushiRestaurant() {
        Optional<Restaurant> restaurantOptional = repository.findById(RestaurantTestData.SUSHI_ID);

        Assertions.assertThat(restaurantOptional).isPresent();
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurantOptional.get(),
                RestaurantTestData.sushiRestaurant);
    }

    @Test
    void findById_AllOk_ReturnEmptyOptional() {
        Assertions.assertThat(repository.findById(TestUtils.NOT_FOUND)).isEmpty();
    }
}
