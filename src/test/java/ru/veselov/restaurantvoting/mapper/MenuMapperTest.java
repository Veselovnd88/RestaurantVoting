package ru.veselov.restaurantvoting.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.util.MenuTestData;

class MenuMapperTest {

    MenuMapper menuMapper = new MenuMapperImpl();
    DishMapper dishMapper = new DishMapperImpl();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(menuMapper, "dishMapper", dishMapper, DishMapper.class);
    }

    @Test
    void toDto_AllOk_ReturnCorrectMappedMenuDto() {
        Assertions.assertThat(menuMapper.toDto(MenuTestData.sushiRestaurantMenu))
                .extracting(MenuDto::getId, MenuDto::getAddedAt, MenuDto::getDishes)
                .containsExactly(MenuTestData.sushiRestaurantMenu.id(),
                        MenuTestData.sushiRestaurantMenu.getAddedAt(),
                        dishMapper.toDtos(MenuTestData.sushiRestaurantMenu.getDishes())
                );
    }
}