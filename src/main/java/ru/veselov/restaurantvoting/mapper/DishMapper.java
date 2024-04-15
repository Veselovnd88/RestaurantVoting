package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.mapper.config.MapStructConfig;
import ru.veselov.restaurantvoting.model.Dish;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface DishMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "id", source = "id")
    DishDto toDto(Dish dish);

    List<DishDto> toDtos(List<Dish> dishes);

    Dish toEntity(DishDto dishDto);
}
