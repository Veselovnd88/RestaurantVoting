package ru.veselov.restaurantvoting.repository;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

import java.util.List;

@DataJpaTest
@Sql(scripts = {"classpath:db/init.sql", "classpath:db/populateDbTest.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class MenuRepositoryTest {

    @Autowired
    MenuRepository menuRepository;

    @Test
    void findAllBetweenDatesByRestaurant() {
        List<Menu> menus = menuRepository.findAllBetweenDatesByRestaurant(RestaurantTestData.sushiRestaurant.id(),
                MenuTestData.ADDED_DATE, MenuTestData.ADDED_DATE);

        Assertions.assertThat(menus).hasSize(1).element(0).extracting(Menu::getAddedAt)
                .asInstanceOf(InstanceOfAssertFactories.LOCAL_DATE)
                .isEqualTo(MenuTestData.ADDED_DATE);
        DishTestData.DISH_MATCHER.assertMatch(menus.get(0).getDishes(), List.of(DishTestData.philadelphia, DishTestData.tastyRoll, DishTestData.unagi));
    }
}