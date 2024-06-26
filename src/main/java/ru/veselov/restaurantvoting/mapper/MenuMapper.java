package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.mapper.annotation.WithDishes;
import ru.veselov.restaurantvoting.mapper.config.MapStructConfig;
import ru.veselov.restaurantvoting.model.Menu;

import java.util.List;

@Mapper(config = MapStructConfig.class,
        uses = {DishMapper.class})
public interface MenuMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "dishes", source = "dishes")
    @WithDishes
    MenuDto toDto(Menu menu);

    @IterableMapping(qualifiedBy = WithDishes.class)
    List<MenuDto> toDtos(List<Menu> menus);

    @Mapping(target = "date", source = "date")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Menu toEntityUpdate(@MappingTarget Menu menu, InputMenuDto menuDto);
}
