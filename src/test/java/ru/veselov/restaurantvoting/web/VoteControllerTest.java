package ru.veselov.restaurantvoting.web;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.VoteDto;
import ru.veselov.restaurantvoting.service.MenuService;
import ru.veselov.restaurantvoting.service.VoteService;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.MockMvcUtils;
import ru.veselov.restaurantvoting.util.UserTestData;
import ru.veselov.restaurantvoting.util.VoteTestData;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

class VoteControllerTest extends AbstractRestControllerTest {

    @Autowired
    VoteService voteService;

    @Autowired
    MenuService menuService;

    @MockBean
    Clock clock;

    @BeforeEach
    void setUp() {
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
    }

    @Test
    @SneakyThrows
    void vote_AllOk_VoteToTheMenu() {
        Mockito.when(clock.instant())
                .thenReturn(LocalDateTime.of(VoteTestData.VOTED_AT_DATE, LocalTime.of(22, 0, 0)).toInstant(ZoneOffset.UTC));

        mockMvc.perform(MockMvcUtils.vote(MenuTestData.BURGER_MENU_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        MenuDto burgerMenu = menuService.getMenuByIdWithDishesAndVotes(MenuTestData.BURGER_MENU_ID);
        Assertions.assertThat(burgerMenu.votes()).flatExtracting(VoteDto::getUser).contains(UserTestData.user1Dto);
        MenuDto sushiMenu = menuService.getMenuByIdWithDishesAndVotes(MenuTestData.SUSHI_MENU_ID);
        Assertions.assertThat(sushiMenu.votes()).flatExtracting(VoteDto::getUser).doesNotContain(UserTestData.user1Dto);
    }

}