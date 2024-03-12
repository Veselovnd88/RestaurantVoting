package ru.veselov.restaurantvoting.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.mapper.DishMapper;
import ru.veselov.restaurantvoting.mapper.RestaurantMapper;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;
import ru.veselov.restaurantvoting.repository.VoteRepository;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.VoteTestData;


@SpringBootTest
@Sql(scripts = {"classpath:db/init.sql", "classpath:db/populateDbTest.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class RestaurantServiceImplTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RestaurantMapper restaurantMapper;

    @Autowired
    RestaurantRepository repository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    DishMapper dishMapper;

    @Test
    void findByIdWithMenuAndVotesBetweenDates_AllOk_ReturnRestaurantDtoWithVoteCount() {
        RestaurantDto foundRestaurant = restaurantService.findByIdWithMenuAndVotesBetweenDates(RestaurantTestData.sushiRestaurant.id(),
                VoteTestData.VOTED_AT_DATE, VoteTestData.VOTED_AT_DATE);

        Assertions.assertThat(foundRestaurant).extracting(RestaurantDto::getVoteCount)
                .isEqualTo(1);

        Assertions.assertThat(foundRestaurant.getMenus()).hasSize(1);

        MenuDto menuDto = foundRestaurant.getMenus().get(0);
        Assertions.assertThat(menuDto.getDishes()).contains(
                dishMapper.toDto(DishTestData.philadelphia),
                dishMapper.toDto(DishTestData.tastyRoll),
                dishMapper.toDto(DishTestData.unagi)
        );
    }
}