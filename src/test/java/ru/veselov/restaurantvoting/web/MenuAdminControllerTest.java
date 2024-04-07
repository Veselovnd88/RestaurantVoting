package ru.veselov.restaurantvoting.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.service.DishService;
import ru.veselov.restaurantvoting.service.MenuService;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

@WithMockUser(value = "ADMIN")
class MenuAdminControllerTest extends AbstractRestControllerTest {

    @Autowired
    MenuService menuService;

    @Autowired
    DishService dishService;

    @Test
    @SneakyThrows
    void create_AllOk_CreateMenu() {
        mockMvc.perform(MockMvcUtils.createMenu(RestaurantTestData.SUSHI_ID, MenuTestData.menuDtoToCreate))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_DTO_MATCHER.contentJson(MenuTestData.createdMenuDto));
    }

    @Test
    @SneakyThrows
    void update_AllOk_UpdateMenuWithNewDish() {
        mockMvc.perform(MockMvcUtils.updateMenu(MenuTestData.SUSHI_MENU_ID, MenuTestData.menuDtoToUpdate))
                .andExpect(MockMvcResultMatchers.status().isOk());
        MenuDto foundMenu = menuService.getMenuById(MenuTestData.SUSHI_MENU_ID);
        Assertions.assertThat(foundMenu.dishes()).hasSize(1).flatExtracting(DishDto::id, DishDto::name, DishDto::price)
                .contains(100022, "veryTasty", 10000);
    }

    @Test
    @SneakyThrows
    void update_AllOk_UpdateMenuWithRenamingDish() {
        mockMvc.perform(MockMvcUtils.updateMenu(MenuTestData.SUSHI_MENU_ID, MenuTestData.menuDtoToUpdateWithChangedDish))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_DTO_MATCHER.contentJson(MenuTestData.menuDtoToUpdateWithChangedDishAfterUpdate));

        MenuDto foundMenu = menuService.getMenuById(MenuTestData.SUSHI_MENU_ID);
        Assertions.assertThat(foundMenu.dishes()).hasSize(3).contains(DishTestData.philadelphiaDto, DishTestData.tastyRollDto,
                DishTestData.changedUnagiDto);
    }

    @Test
    @SneakyThrows
    void delete_AllOk_ReturnNoContent() {
        mockMvc.perform(MockMvcUtils.deleteMenu(MenuTestData.SUSHI_MENU_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThatThrownBy(() -> menuService.getMenuById(MenuTestData.SUSHI_MENU_ID))
                .isInstanceOf(EntityNotFoundException.class);
        Assertions.assertThatThrownBy(() -> dishService.findOne(DishTestData.TASTY_ROLL_ID))
                .isInstanceOf(EntityNotFoundException.class);
        Assertions.assertThatThrownBy(() -> dishService.findOne(DishTestData.UNAGI_ID))
                .isInstanceOf(EntityNotFoundException.class);
        Assertions.assertThatThrownBy(() -> dishService.findOne(DishTestData.PHILADELPHIA_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
