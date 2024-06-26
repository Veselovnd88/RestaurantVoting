package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.veselov.restaurantvoting.dto.InputRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.mapper.annotation.WithDishes;
import ru.veselov.restaurantvoting.mapper.annotation.WithMenu;
import ru.veselov.restaurantvoting.mapper.annotation.WithoutMenu;
import ru.veselov.restaurantvoting.mapper.config.MapStructConfig;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.util.List;

@Mapper(config = MapStructConfig.class,
        uses = MenuMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestaurantMapper {

    @Mapping(target = "menus", qualifiedBy = WithDishes.class)
    @WithMenu
    RestaurantDto entityToDtoWithMenus(Restaurant restaurant);


    @Mapping(target = "menus", ignore = true)
    @WithoutMenu
    RestaurantDto entityToDto(Restaurant restaurant);

    @IterableMapping(qualifiedBy = WithoutMenu.class)
    List<RestaurantDto> entitiesToDto(List<Restaurant> restaurants);

    @IterableMapping(qualifiedBy = WithMenu.class)
    List<RestaurantDto> entitiesToDtoWithMenus(List<Restaurant> restaurants);

    Restaurant toEntity(InputRestaurantDto restaurantDto);
}
