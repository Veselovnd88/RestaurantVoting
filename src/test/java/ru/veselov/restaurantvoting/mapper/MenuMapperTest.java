package ru.veselov.restaurantvoting.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;

import java.util.List;
import java.util.Set;

class MenuMapperTest {

    MenuMapper menuMapper = new MenuMapperImpl();

    public MenuMapperTest() {
        ReflectionTestUtils.setField(menuMapper, "dishMapper", new DishMapperImpl(), DishMapper.class);
        ReflectionTestUtils.setField(menuMapper, "voteMapper", new VoteMapperImpl(), VoteMapper.class);
    }

    @Test
    void toDto_AllOk_ReturnCorrectMappedMenuDto() {
        MenuDto menuDto = menuMapper.toDto(MenuTestData.sushiRestaurantMenuWithVotes);

        Assertions.assertThat(menuDto).isEqualTo(MenuTestData.sushiRestaurantMenuDtoWithVotes);
    }

    @Test
    void toDtoWithoutVotes_AllOk_ReturnDtoWithoutVotes() {
        MenuDto menuDto = menuMapper.toDtoWithoutVotes(MenuTestData.sushiRestaurantMenuWithVotes);

        Assertions.assertThat(menuDto).isEqualTo(MenuTestData.sushiRestaurantMenuDto);
    }

    @Test
    void toDtosWithoutVotes_AllOk_ReturnEntityWIthDishesAndDate() {
        List<MenuDto> dtos = menuMapper.toDtos(List.of(MenuTestData.sushiRestaurantMenuWithVotes));

        Assertions.assertThat(dtos).hasSameElementsAs(List.of(MenuTestData.sushiRestaurantMenuDto));
    }

    @Test
    void toEntity_AllOk_ReturnEntityWithDishes() {
        Menu menu = menuMapper.toEntity(MenuTestData.menuDtoToCreate);

        MenuTestData.MENU_MATCHER.assertMatch(menu, MenuTestData.menuToCreateWithoutId);
        DishTestData.DISH_MATCHER.assertMatch(menu.getDishes(), Set.of(DishTestData.tastyDishEntity));
    }
}
