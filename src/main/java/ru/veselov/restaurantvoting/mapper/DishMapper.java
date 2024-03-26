package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.dto.NewDishDto;
import ru.veselov.restaurantvoting.model.Dish;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DishMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "id", source = "id")
    DishDto toDto(Dish dish);

    List<DishDto> toDtos(List<Dish> dishes);

    Dish toEntity(NewDishDto dishDto);
}
