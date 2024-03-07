package ru.veselov.restaurantvoting.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.veselov.restaurantvoting.model.Vote;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

import java.util.List;

class RestaurantMapperTest {

    RestaurantMapper restaurantMapper = new RestaurantMapperImpl();

    @Test
    void toRestaurantTo_OneVote_MapToOneVote() {
        RestaurantTestData.sushiRestaurant.setVotes(List.of(new Vote()));
        Assertions.assertThat(restaurantMapper.entityToDto(RestaurantTestData.sushiRestaurant).getVoteCount())
                .isEqualTo(1);
    }

    @Test
    void toRestaurantTo_NullVotes_MapToZeroVotes() {
        Assertions.assertThat(restaurantMapper.entityToDto(RestaurantTestData.sushiRestaurant).getVoteCount()).isZero();
    }
}