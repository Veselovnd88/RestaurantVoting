package ru.veselov.restaurantvoting.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;

import java.time.LocalDate;

class MenuMapperTest {

    MenuMapper menuMapper = new MenuMapperImpl();

    public MenuMapperTest() {
        ReflectionTestUtils.setField(menuMapper, "dishMapper", new DishMapperImpl(), DishMapper.class);
    }

    @Test
    void toEntity_AllOk_ReturnEntityWithDishes() {
        Menu menu = menuMapper.toEntity(MenuTestData.menuDtoToCreate);

        MenuTestData.MENU_MATCHER.assertMatch(menu, MenuTestData.menuToCreateWithoutId);
        DishTestData.DISH_MATCHER.assertMatch(menu.getDishes(), DishTestData.getUpdatedDishesInSortedSet());
    }

    @Test
    void toEntityUpdate_AllOk_ReturnEntityWithDishes() {
        Menu menuToUpdate = new Menu(null, LocalDate.of(2019, 3, 3), null);
        Menu menu = menuMapper.toEntityUpdate(menuToUpdate, MenuTestData.menuDtoToCreate);

        Assertions.assertThat(menu).extracting(Menu::getDate).isEqualTo(MenuTestData.MENU_DATE.plusDays(1));
        DishTestData.DISH_MATCHER.assertMatch(menu.getDishes(), DishTestData.getUpdatedDishesInSortedSet());
    }
}
