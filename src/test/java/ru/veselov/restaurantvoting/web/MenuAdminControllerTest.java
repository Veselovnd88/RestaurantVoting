package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.exception.NotFoundException;
import ru.veselov.restaurantvoting.exception.RestaurantNotFoundException;
import ru.veselov.restaurantvoting.service.dish.DishService;
import ru.veselov.restaurantvoting.service.menu.MenuService;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.util.SecurityUtils;
import ru.veselov.restaurantvoting.util.TestUtils;
import ru.veselov.restaurantvoting.util.UserTestData;
import ru.veselov.restaurantvoting.web.menu.MenuAdminController;

class MenuAdminControllerTest extends AbstractRestControllerTest {

    @Autowired
    MenuService menuService;

    @Autowired
    DishService dishService;

    @Test
    @SneakyThrows
    void create_AllOk_CreateMenu() {
        mockMvc.perform(MockMvcUtils.createMenu(RestaurantTestData.SUSHI_ID, MenuTestData.MENU_DATE.plusDays(1))
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_DTO_MATCHER.contentJson(MenuTestData.createdMenuDto));
    }

    @Test
    @SneakyThrows
    void create_MenuAlreadyExists_ReturnError() {
        ResultActions resultActions = mockMvc
                .perform(MockMvcUtils.createMenu(RestaurantTestData.SUSHI_ID, MenuTestData.MENU_DATE)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkConflictError(resultActions,
                GlobalExceptionHandler.MENU_FOR_RESTAURANT_FOR_DATE_EXISTS,
                MenuAdminController.REST_URL);
    }

    @Test
    @SneakyThrows
    void create_RestaurantNotFound_ReturnError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createMenu(TestUtils.NOT_FOUND,
                        MenuTestData.MENU_DATE)
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkNotFoundFields(resultActions,
                RestaurantNotFoundException.MSG_WITH_ID.formatted(TestUtils.NOT_FOUND),
                MenuAdminController.REST_URL);
    }

    @Test
    @SneakyThrows
    void update_AllOk_UpdateMenuWithNewDish() {
        mockMvc.perform(MockMvcUtils.updateMenu(MenuTestData.SUSHI_MENU_ID, MenuTestData.menuDtoToUpdate)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        MenuDto foundMenu = menuService.getMenuByIdWithDishes(MenuTestData.SUSHI_MENU_ID);
        Assertions.assertThat(foundMenu.date()).isEqualTo(MenuTestData.menuDtoToUpdate.date());
    }

    @Test
    @SneakyThrows
    void update_ChangeToDateOfExistingMenu_ReturnError() {
        MenuDto menuDto = menuService.create(RestaurantTestData.SUSHI_ID, MenuTestData.MENU_DATE.plusDays(1));
        InputMenuDto menuDtoToUpdateForConflict = new InputMenuDto(menuDto.id(), MenuTestData.MENU_DATE);

        ResultActions resultActions = mockMvc
                .perform(MockMvcUtils.updateMenu(menuDto.id(), menuDtoToUpdateForConflict)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkConflictError(resultActions,
                GlobalExceptionHandler.MENU_FOR_RESTAURANT_FOR_DATE_EXISTS,
                MockMvcUtils.MENU_ID_URL.formatted(menuDto.id()));
    }

    @Test
    @SneakyThrows
    void delete_AllOk_ReturnNoContent() {
        mockMvc.perform(MockMvcUtils.deleteMenu(MenuTestData.SUSHI_MENU_ID)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThatThrownBy(() -> menuService.getMenuByIdWithDishes(MenuTestData.SUSHI_MENU_ID))
                .isInstanceOf(NotFoundException.class);
        Assertions.assertThatThrownBy(() -> dishService.findOne(DishTestData.TASTY_ROLL_ID))
                .isInstanceOf(NotFoundException.class);
        Assertions.assertThatThrownBy(() -> dishService.findOne(DishTestData.UNAGI_ID))
                .isInstanceOf(NotFoundException.class);
        Assertions.assertThatThrownBy(() -> dishService.findOne(DishTestData.PHILADELPHIA_ID))
                .isInstanceOf(NotFoundException.class);
    }
}
