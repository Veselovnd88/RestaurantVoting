package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

import java.util.List;

class MenuControllerTest extends AbstractRestControllerTest {

    @Test
    @SneakyThrows
    void getMenuById_AllOk_ReturnMenuDto() {
        mockMvc.perform(MockMvcUtils.getMenu(MenuTestData.SUSHI_MENU_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_DTO_MATCHER.contentJson(MenuTestData.sushiRestaurantMenuDtoWithVotes));
    }

    @Test
    @SneakyThrows
    void getMenusByRestaurant_AllOk_ReturnListOfMenuDto() {
        mockMvc.perform(MockMvcUtils.getMenusByRestaurantId(RestaurantTestData.SUSHI_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_DTO_MATCHER.contentJson(List.of(MenuTestData.sushiRestaurantMenuDto)));
    }
}
