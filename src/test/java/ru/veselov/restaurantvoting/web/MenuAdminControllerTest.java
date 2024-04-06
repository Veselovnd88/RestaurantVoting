package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.service.MenuService;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

@WithMockUser(value = "ADMIN")
class MenuAdminControllerTest extends AbstractRestControllerTest {

    @Autowired
    MenuService menuService;

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

}
