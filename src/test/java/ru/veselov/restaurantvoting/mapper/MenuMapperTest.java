package ru.veselov.restaurantvoting.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.VoteTestData;

import java.util.List;

class MenuMapperTest {

    MenuMapper menuMapper = new MenuMapperImpl();

    public MenuMapperTest() {
        ReflectionTestUtils.setField(menuMapper, "dishMapper", new DishMapperImpl(), DishMapper.class);
        ReflectionTestUtils.setField(menuMapper, "voteMapper", new VoteMapperImpl(), VoteMapper.class);
    }

    @Test
    void toDto_AllOk_ReturnCorrectMappedMenuDto() {
        MenuTestData.sushiRestaurantMenu.setVotes(List.of(VoteTestData.user1VoteSushi));

        MenuDto menuDto = menuMapper.toDto(MenuTestData.sushiRestaurantMenu);

        Assertions.assertThat(menuDto).isEqualTo(MenuTestData.sushiRestaurantMenuDto);
    }
}