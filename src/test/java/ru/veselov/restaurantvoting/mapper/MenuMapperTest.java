package ru.veselov.restaurantvoting.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.extension.MapperParameterResolver;
import ru.veselov.restaurantvoting.extension.annotation.InjectMapper;
import ru.veselov.restaurantvoting.util.MenuTestData;

class MenuMapperTest {

    MenuMapper menuMapper;

    public MenuMapperTest(@InjectMapper MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Test
    void toDto_AllOk_ReturnCorrectMappedMenuDto(@InjectMapper DishMapper dishMapper) {
        Assertions.assertThat(menuMapper.toDto(MenuTestData.sushiRestaurantMenu))
                .extracting(MenuDto::getId, MenuDto::getAddedAt, MenuDto::getDishes)
                .containsExactly(MenuTestData.sushiRestaurantMenu.id(),
                        MenuTestData.sushiRestaurantMenu.getAddedAt(),
                        dishMapper.toDtos(MenuTestData.sushiRestaurantMenu.getDishes())
                );
    }
}