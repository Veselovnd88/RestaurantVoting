package ru.veselov.restaurantvoting.web.validation;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.service.menu.MenuService;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.ResultActionErrorsUtil;
import ru.veselov.restaurantvoting.web.GlobalExceptionHandler;
import ru.veselov.restaurantvoting.web.menu.MenuAdminController;

@ActiveProfiles("test")
@WebMvcTest(value = MenuAdminController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class MenuAdminControllerValidationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MenuService menuService;

    @Test
    @SneakyThrows
    void update_MenuWithoutId_ReturnValidationError() {
        InputMenuDto menuDtoToUpdateWithoutId = new InputMenuDto(null, MenuTestData.MENU_DATE);

        ResultActions resultActions = mockMvc
                .perform(MockMvcUtils.updateMenu(MenuTestData.SUSHI_MENU_ID, menuDtoToUpdateWithoutId));

        ResultActionErrorsUtil.checkRequestValidationError(resultActions,
                GlobalExceptionHandler.REQUEST_VALIDATION_FAILED,
                MockMvcUtils.MENU_ID_URL.formatted(MenuTestData.SUSHI_MENU_ID));
    }
}
