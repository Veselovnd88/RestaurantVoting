package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.to.RestaurantTo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    @Mapping(target = "voteCount", expression = "java(restaurant.getVotes()==null?0:restaurant.getVotes().size())")
    RestaurantTo toRestaurantTo(Restaurant restaurant);

    Restaurant toEntity(RestaurantTo restaurantTo);
}
