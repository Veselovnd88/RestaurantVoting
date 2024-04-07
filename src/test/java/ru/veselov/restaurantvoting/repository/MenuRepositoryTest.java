package ru.veselov.restaurantvoting.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.VoteTestData;

import java.util.Optional;

@DataJpaTest
@Sql(scripts = {"classpath:db/init.sql", "classpath:db/populateDbTest.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class MenuRepositoryTest {

    @Autowired
    MenuRepository menuRepository;

    @Test
    void findOneById_AllOK_ReturnMenuWithDishesAndVotes() {
        Optional<Menu> foundById = menuRepository.findByIdWithDishesAndVotes(100006);

        Assertions.assertThat(foundById).isPresent();
        Menu menu = foundById.get();
        MenuTestData.MENU_MATCHER.assertMatch(menu, MenuTestData.sushiRestaurantMenu);
        DishTestData.DISH_MATCHER.assertMatch(menu.getDishes(), DishTestData.sushiDishes);
        VoteTestData.VOTE_MATCHER.assertMatch(menu.getVotes(), VoteTestData.sushiVotes);
    }
}