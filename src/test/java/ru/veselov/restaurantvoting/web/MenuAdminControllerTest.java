package ru.veselov.restaurantvoting.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.exception.RestaurantNotFoundException;
import ru.veselov.restaurantvoting.service.DishService;
import ru.veselov.restaurantvoting.service.MenuService;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.util.SecurityUtils;
import ru.veselov.restaurantvoting.util.UserTestData;

import java.util.List;

@WithMockUser(value = "ADMIN")
class MenuAdminControllerTest extends AbstractRestControllerTest {

    @Autowired
    MenuService menuService;

    @Autowired
    DishService dishService;

    @Test
    @SneakyThrows
    void create_AllOk_CreateMenu() {
        mockMvc.perform(MockMvcUtils.createMenu(RestaurantTestData.SUSHI_ID, MenuTestData.menuDtoToCreate)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_DTO_MATCHER.contentJson(MenuTestData.createdMenuDto));
    }

    @Test
    @SneakyThrows
    void create_MenuAlreadyExists_ReturnError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createMenu(RestaurantTestData.SUSHI_ID, MenuTestData.menuDtoToCreateForConflict)
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkConflictError(resultActions,
                GlobalExceptionHandler.MENU_FOR_RESTAURANT_FOR_DATE_EXISTS,
                MockMvcUtils.MENU_RESTAURANTS_ID_URL.formatted(RestaurantTestData.SUSHI_ID));
    }

    @Test
    @SneakyThrows
    void create_RestaurantNotFound_ReturnError() {
        ResultActions resultActions = mockMvc.perform(MockMvcUtils.createMenu(RestaurantTestData.NOT_FOUND,
                        MenuTestData.menuDtoToCreateForConflict)
                .with(SecurityUtils.userHttpBasic(UserTestData.admin)));

        ResultActionErrorsUtil.checkNotFoundFields(resultActions,
                RestaurantNotFoundException.MESSAGE_WITH_ID.formatted(RestaurantTestData.NOT_FOUND));
    }

    @Test
    @SneakyThrows
    void update_AllOk_UpdateMenuWithNewDish() {
        mockMvc.perform(MockMvcUtils.updateMenu(MenuTestData.SUSHI_MENU_ID, MenuTestData.menuDtoToUpdate)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        MenuDto foundMenu = menuService.getMenuByIdWithDishesAndVotes(MenuTestData.SUSHI_MENU_ID);
        Assertions.assertThat(foundMenu.dishes()).hasSize(2).flatExtracting(DishDto::id, DishDto::name, DishDto::price)
                .containsExactly(100022, "veryTasty", 10000, 100023, "veryTasty2", 10000);
    }

    @Test
    @SneakyThrows
    void update_AllOk_UpdateMenuWithRenamingDish() {
        mockMvc.perform(MockMvcUtils.updateMenu(MenuTestData.SUSHI_MENU_ID, MenuTestData.menuDtoToUpdateWithChangedDish)
                        .with(SecurityUtils.userHttpBasic(UserTestData.admin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_DTO_MATCHER.contentJson(MenuTestData.menuDtoToUpdateWithChangedDishAfterUpdate));

        MenuDto foundMenu = menuService.getMenuByIdWithDishesAndVotes(MenuTestData.SUSHI_MENU_ID);
        Assertions.assertThat(foundMenu.dishes()).hasSize(3).contains(DishTestData.philadelphiaDto, DishTestData.tastyRollDto,
                DishTestData.changedUnagiDto);
    }

    @Test
    @SneakyThrows
    void update_ChangeToDateOfExistingMenu_ReturnError() {
        MenuDto menuDto = menuService.create(RestaurantTestData.SUSHI_ID, MenuTestData.menuDtoToCreate);
        InputMenuDto menuDtoToUpdateForConflict =
                new InputMenuDto(menuDto.id(), MenuTestData.ADDED_DATE,
                        List.of(DishTestData.savedWithMenuNewTastyDish, DishTestData.savedWithMenuNewTastyDish2));
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

        Assertions.assertThatThrownBy(() -> menuService.getMenuByIdWithDishesAndVotes(MenuTestData.SUSHI_MENU_ID))
                .isInstanceOf(EntityNotFoundException.class);
        Assertions.assertThatThrownBy(() -> dishService.findOne(DishTestData.TASTY_ROLL_ID))
                .isInstanceOf(EntityNotFoundException.class);
        Assertions.assertThatThrownBy(() -> dishService.findOne(DishTestData.UNAGI_ID))
                .isInstanceOf(EntityNotFoundException.class);
        Assertions.assertThatThrownBy(() -> dishService.findOne(DishTestData.PHILADELPHIA_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
