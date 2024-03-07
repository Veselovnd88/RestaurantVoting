package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.to.RestaurantTo;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    @Mapping(target = "voteCount", expression = "java(restaurant.getVotes()==null?0:restaurant.getVotes().size())")
    RestaurantTo entityToDto(Restaurant restaurant);

    List<RestaurantTo> entitiesToDto(List<Restaurant> restaurants);

    Restaurant toEntity(RestaurantTo restaurantTo);
}
