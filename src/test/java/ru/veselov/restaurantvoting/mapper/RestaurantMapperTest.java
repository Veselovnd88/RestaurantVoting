package ru.veselov.restaurantvoting.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.veselov.restaurantvoting.extension.MapperParameterResolver;
import ru.veselov.restaurantvoting.extension.annotation.InjectMapper;
import ru.veselov.restaurantvoting.model.Vote;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

import java.util.List;

@ExtendWith(MapperParameterResolver.class)
class RestaurantMapperTest {

    RestaurantMapper restaurantMapper;

    public RestaurantMapperTest(@InjectMapper RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    @Test
    void toRestaurantTo_OneVote_MapToOneVote() {
        RestaurantTestData.sushiRestaurant.setVotes(List.of(new Vote()));
        Assertions.assertThat(restaurantMapper.entityToDtoWithMenus(RestaurantTestData.sushiRestaurant).getVoteCount())
                .isEqualTo(1);
    }

    @Test
    void toRestaurantTo_NullVotes_MapToZeroVotes() {
        Assertions.assertThat(restaurantMapper.entityToDtoWithMenus(RestaurantTestData.sushiRestaurant).getVoteCount()).isZero();
    }
}