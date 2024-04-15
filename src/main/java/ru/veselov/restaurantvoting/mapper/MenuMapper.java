package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.mapper.annotation.WithVotesAndDishes;
import ru.veselov.restaurantvoting.mapper.annotation.WithoutVotes;
import ru.veselov.restaurantvoting.mapper.config.MapStructConfig;
import ru.veselov.restaurantvoting.model.Dish;
import ru.veselov.restaurantvoting.model.Menu;

import java.util.List;
import java.util.Set;

@Mapper(config = MapStructConfig.class,
        uses = {DishMapper.class, VoteMapper.class})
public interface MenuMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "addedAt", source = "addedAt")
    @Mapping(target = "dishes", source = "dishes")
    @Mapping(target = "votes", source = "votes")
    @WithVotesAndDishes
    MenuDto toDto(Menu menu);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "addedAt", source = "addedAt")
    @Mapping(target = "dishes", source = "dishes")
    @Mapping(target = "votes", ignore = true)
    @WithoutVotes
    MenuDto toDtoWithoutVotes(Menu menu);

    @IterableMapping(qualifiedBy = WithoutVotes.class)
    List<MenuDto> toDtosWithoutVotes(List<Menu> menus);

    @WithVotesAndDishes
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

    @AfterMapping
    default void bindMenuToDishes(@MappingTarget Menu menu) {
        Set<Dish> dishes = menu.getDishes();
        if (dishes == null) return;
        for (Dish dish : dishes) {
            dish.setMenu(menu);
        }
    }
}
