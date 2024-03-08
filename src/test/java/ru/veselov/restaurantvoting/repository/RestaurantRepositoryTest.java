package ru.veselov.restaurantvoting.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@Sql(scripts = {"classpath:db/init.sql", "classpath:db/populateDbTest.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository repository;

    @Test
    void findAllWithMenus_ReturnAllRestaurantsWithAllMenus() {
        List<Restaurant> allWithMenus = repository.findAllWithMenuByDate(LocalDate.of(2024, 3, 6));

        Assertions.assertThat(allWithMenus).hasSize(RestaurantTestData.DB_COUNT)
                .containsExactlyInAnyOrder(RestaurantTestData.sushiRestaurant, RestaurantTestData.pizzaRestaurant,
                        RestaurantTestData.burgerRestaurant);
    }
}
