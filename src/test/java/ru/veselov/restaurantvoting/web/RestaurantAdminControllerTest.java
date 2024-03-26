package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

@SpringBootTest
@Sql(scripts = {"classpath:db/init.sql", "classpath:db/populateDbTest.sql"}, config = @SqlConfig(encoding = "UTF-8"))
@WithMockUser(roles = "ADMIN")
@AutoConfigureMockMvc
class RestaurantAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void create_AllOk_CreateRestaurantReturnDtoAndResourceUri() {
        mockMvc.perform(MockMvcUtils.createRestaurant(RestaurantTestData.newRestaurantDto))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(RestaurantTestData.RESTAURANT_DTO_MATCHER.contentJson(RestaurantTestData.savedRestaurantDto));
    }

    @Test
    @SneakyThrows
    void update_AllOk_UpdateRestaurantAndReturnUpdated() {
        mockMvc.perform(MockMvcUtils.updateRestaurant(RestaurantTestData.restaurantDtoToUpdate, RestaurantTestData.SUSHI_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(RestaurantTestData.RESTAURANT_DTO_MATCHER.contentJson(RestaurantTestData.sushiRestaurantUpdated));
    }

    @Test
    @SneakyThrows
    void delete_AllOk_DeleteRestaurantAndReturnNoContent() {
        mockMvc.perform(MockMvcUtils.deleteRestaurant(RestaurantTestData.SUSHI_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
