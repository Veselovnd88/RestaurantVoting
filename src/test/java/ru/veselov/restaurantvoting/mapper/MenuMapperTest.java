package ru.veselov.restaurantvoting.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;

import java.time.LocalDate;
import java.util.List;

class MenuMapperTest {

    MenuMapper menuMapper = new MenuMapperImpl();

    public MenuMapperTest() {
        VoteMapperImpl voteMapper = new VoteMapperImpl();
        ReflectionTestUtils.setField(voteMapper, "userMapper", new UserMapperImpl(), UserMapper.class);
        ReflectionTestUtils.setField(menuMapper, "dishMapper", new DishMapperImpl(), DishMapper.class);
        ReflectionTestUtils.setField(menuMapper, "voteMapper", voteMapper, VoteMapper.class);
    }

    @Test
    void toDto_AllOk_ReturnCorrectMappedMenuDto() {
        MenuDto menuDto = menuMapper.toDto(MenuTestData.getSushiRestaurantMenuWithVotes());

        Assertions.assertThat(menuDto).isEqualTo(MenuTestData.sushiRestaurantMenuDtoWithVotes);
    }

    @Test
    void toDtoWithoutVotes_AllOk_ReturnDtoWithoutVotes() {
        MenuDto menuDto = menuMapper.toDtoWithoutVotes(MenuTestData.getSushiRestaurantMenuWithVotes());

        Assertions.assertThat(menuDto).isEqualTo(MenuTestData.sushiRestaurantMenuDto);
    }

    @Test
    void toDtosWithoutVotesWithoutVotes_AllOk_ReturnEntityWIthDishesAndDate() {
        List<MenuDto> dtos = menuMapper.toDtosWithoutVotes(List.of(MenuTestData.getSushiRestaurantMenuWithVotes()));

        Assertions.assertThat(dtos).hasSameElementsAs(List.of(MenuTestData.sushiRestaurantMenuDto));
    }

    @Test
    void toEntity_AllOk_ReturnEntityWithDishes() {
        Menu menu = menuMapper.toEntity(MenuTestData.menuDtoToCreate);

        MenuTestData.MENU_MATCHER.assertMatch(menu, MenuTestData.menuToCreateWithoutId);
        DishTestData.DISH_MATCHER.assertMatch(menu.getDishes(), DishTestData.getUpdatedDishesInSortedSet());
    }

    @Test
    void toEntityUpdate_AllOk_ReturnEntityWithDishes() {
        Menu menuToUpdate = new Menu(null, LocalDate.of(2019, 3, 3), null, null, null);
        Menu menu = menuMapper.toEntityUpdate(menuToUpdate, MenuTestData.menuDtoToCreate);

        Assertions.assertThat(menu).extracting(Menu::getDate).isEqualTo(MenuTestData.ADDED_DATE.plusDays(1));
        DishTestData.DISH_MATCHER.assertMatch(menu.getDishes(), DishTestData.getUpdatedDishesInSortedSet());
    }
}
