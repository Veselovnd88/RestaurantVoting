package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.model.Menu;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DishMapper.class)
public interface MenuMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "addedAt", source = "addedAt")
    @Mapping(target = "dishes", source = "dishes")
    MenuDto toDto(Menu menu);
}
