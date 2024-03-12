package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MenuMapper.class)
public interface RestaurantMapper {

    @Mapping(target = "voteCount", expression = "java(restaurant.getVotes()==null?0:restaurant.getVotes().size())")
    RestaurantDto entityToDto(Restaurant restaurant);

    List<RestaurantDto> entitiesToDto(List<Restaurant> restaurants);

    Restaurant toEntity(RestaurantDto restaurantDto);
}
