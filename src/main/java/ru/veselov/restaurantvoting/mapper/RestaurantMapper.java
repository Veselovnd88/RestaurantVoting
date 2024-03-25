package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.mapper.annotation.WithoutMenu;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MenuMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestaurantMapper {

    @Mapping(target = "voteCount", expression = "java(restaurant.getVotes()==null?0:restaurant.getVotes().size())")
    RestaurantDto entityToDtoWithMenus(Restaurant restaurant);


    @Mapping(target = "menus", ignore = true)
    @Mapping(target = "voteCount", expression = "java(restaurant.getVotes()==null?0:restaurant.getVotes().size())")
    @WithoutMenu
    RestaurantDto entityToDto(Restaurant restaurant);

    @IterableMapping(qualifiedBy = WithoutMenu.class)
    List<RestaurantDto> entitiesToDto(List<Restaurant> restaurants);

    Restaurant toEntity(NewRestaurantDto restaurantDto);
}
