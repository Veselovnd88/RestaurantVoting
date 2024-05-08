package ru.veselov.restaurantvoting.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.exception.NotFoundException;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;
import ru.veselov.restaurantvoting.repository.VoteRepository;
import ru.veselov.restaurantvoting.service.restaurant.RestaurantService;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.TestUtils;
import ru.veselov.restaurantvoting.util.VoteTestData;

import java.util.List;


@SpringBootTest
@Sql(scripts = {"classpath:db/init.sql", "classpath:db/populateDbTest.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class RestaurantServiceImplTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RestaurantRepository repository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    MenuRepository menuRepository;

    @Test
    void getAll_AllOk_ReturnAllRestaurants() {
        List<RestaurantDto> allRestaurants = restaurantService.getAll();

        Assertions.assertThat(allRestaurants).isEqualTo(RestaurantTestData.restaurantDtos);
    }

    @Test
    void findById_AllOk_ReturnOneRestaurant() {
        Assertions.assertThat(restaurantService.findById(RestaurantTestData.SUSHI_ID))
                .isEqualTo(RestaurantTestData.sushiRestaurantDto);
    }

    @Test
    void findById_NotFound_ThrowException() {
        Assertions.assertThatThrownBy(() -> restaurantService.findById(TestUtils.NOT_FOUND))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void findByIdWithMenuAndVotesForDate_AllOk_ReturnDtoWithMenuForDate() {
        RestaurantDto foundRestaurant = restaurantService
                .findByIdWithMenuForDate(RestaurantTestData.SUSHI_ID, VoteTestData.VOTED_AT_DATE);

        Assertions.assertThat(foundRestaurant).isEqualTo(RestaurantTestData.getSushiRestaurantDtoWithMenuByDate());
    }

    @Test
    void findByIdWithMenuForDate_NotFound_ThrowException() {
        Assertions.assertThatThrownBy(() -> restaurantService
                        .findByIdWithMenuForDate(TestUtils.NOT_FOUND, VoteTestData.VOTED_AT_DATE))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_AllOk_CreateAndReturnSavedRestaurantDto() {
        RestaurantDto restaurantDto = restaurantService.create(RestaurantTestData.inputRestaurantDto);

        Assertions.assertThat(restaurantDto).isEqualTo(RestaurantTestData.savedRestaurantDto);
    }

    @Test
    void update_AllOk_UpdateAndReturnUpdated() {
        RestaurantDto update = restaurantService.update(RestaurantTestData.SUSHI_ID, RestaurantTestData.restaurantDtoToUpdate);

        Assertions.assertThat(update).isEqualTo(RestaurantTestData.sushiRestaurantUpdated);
        Assertions.assertThat(restaurantService.findById(RestaurantTestData.SUSHI_ID))
                .isEqualTo(RestaurantTestData.sushiRestaurantUpdated);
    }

    @Test
    void delete_AllOk_DeleteWithMenusAndVotes() {
        restaurantService.delete(RestaurantTestData.SUSHI_ID);

        Assertions.assertThatThrownBy(() -> restaurantService.findById(RestaurantTestData.SUSHI_ID))
                .isInstanceOf(NotFoundException.class);
    }
}