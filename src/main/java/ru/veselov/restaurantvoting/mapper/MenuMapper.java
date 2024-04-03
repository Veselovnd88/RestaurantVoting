package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.mapper.annotation.WithoutVotes;
import ru.veselov.restaurantvoting.model.Menu;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {DishMapper.class, VoteMapper.class})
public interface MenuMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "addedAt", source = "addedAt")
    @Mapping(target = "dishes", source = "dishes")
    @Mapping(target = "votes", source = "votes")
    MenuDto toDto(Menu menu);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "addedAt", source = "addedAt")
    @Mapping(target = "dishes", source = "dishes")
    @Mapping(target = "votes", ignore = true)
    @WithoutVotes
    MenuDto toDtoWithoutVotes(Menu menu);

    @IterableMapping(qualifiedBy = WithoutVotes.class)
    List<MenuDto> toDtos(List<Menu> menus);

    @Mapping(target = "addedAt", source = "addedAt")
    @Mapping(target = "dishes", source = "dishes")
    Menu toEntity(NewMenuDto menuDto);

    @Mapping(target = "addedAt", source = "addedAt")
    @Mapping(target = "dishes", source = "dishes")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "votes", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Menu toEntityUpdate(@MappingTarget Menu menu, NewMenuDto menuDto);
}
