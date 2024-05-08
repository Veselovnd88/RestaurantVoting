package ru.veselov.restaurantvoting.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.exception.MenuNotFoundException;
import ru.veselov.restaurantvoting.exception.NotFoundException;
import ru.veselov.restaurantvoting.exception.RestaurantNotFoundException;
import ru.veselov.restaurantvoting.mapper.DishMapper;
import ru.veselov.restaurantvoting.mapper.DishMapperImpl;
import ru.veselov.restaurantvoting.mapper.MenuMapper;
import ru.veselov.restaurantvoting.mapper.MenuMapperImpl;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;
import ru.veselov.restaurantvoting.service.menu.MenuServiceImpl;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.TestUtils;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    @Mock
    MenuRepository menuRepository;

    @Mock
    RestaurantRepository restaurantRepository;

    @InjectMocks
    MenuServiceImpl menuService;

    @Captor
    ArgumentCaptor<Menu> menuArgumentCaptor;

    @BeforeEach
    void setUp() {
        MenuMapperImpl menuMapper = new MenuMapperImpl();
        ReflectionTestUtils.setField(menuMapper, "dishMapper", new DishMapperImpl(), DishMapper.class);
        ReflectionTestUtils.setField(menuService, "mapper", menuMapper, MenuMapper.class);
    }

    @Test
    void create_AllOk_ShouldSaveNewMenuWithDishes() {
        Mockito.when(restaurantRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(RestaurantTestData.sushiRestaurant));

        menuService.create(RestaurantTestData.SUSHI_ID, MenuTestData.MENU_DATE);

        Mockito.verify(menuRepository, Mockito.times(1)).save(menuArgumentCaptor.capture());
        Menu captured = menuArgumentCaptor.getValue();
        Assertions.assertThat(captured).extracting(Menu::getDate, Menu::getRestaurant)
                .contains(MenuTestData.MENU_DATE, RestaurantTestData.sushiRestaurant);
    }

    @Test
    void create_NoRestaurant_ThrowException() {
        Mockito.when(restaurantRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RestaurantNotFoundException.class)
                .isThrownBy(() -> menuService.create(RestaurantTestData.SUSHI_ID, MenuTestData.MENU_DATE))
                .isInstanceOf(NotFoundException.class)
                .withMessage(RestaurantNotFoundException.MSG_WITH_ID.formatted(RestaurantTestData.SUSHI_ID));
        Mockito.verifyNoInteractions(menuRepository);
    }

    @Test
    void update_AllOk_UpdateMenuAndReturnUpdated() {
        Mockito.when(menuRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(MenuTestData.getGetSushiRestaurantMenu()));

        menuService.update(MenuTestData.SUSHI_MENU_ID, MenuTestData.menuDtoToUpdate);

        Mockito.verify(menuRepository, Mockito.times(1)).save(menuArgumentCaptor.capture());
        Menu captured = menuArgumentCaptor.getValue();
        Assertions.assertThat(captured).extracting(Menu::getDate).isEqualTo(MenuTestData.MENU_DATE.plusDays(5));
    }

    @Test
    void update_MenuNotFound_ThrowException() {
        Mockito.when(menuRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> menuService.update(MenuTestData.SUSHI_MENU_ID, MenuTestData.menuDtoToUpdate))
                .isInstanceOf(NotFoundException.class);
        Mockito.verify(menuRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void getMenuById_AllOk_ReturnMenuDtoWithDishes() {
        Mockito.when(menuRepository.findByIdWithDishes(Mockito.anyInt())).thenReturn(Optional.of(MenuTestData.getGetSushiRestaurantMenu()));

        MenuDto menuById = menuService.getMenuByIdWithDishes(MenuTestData.SUSHI_MENU_ID);

        Assertions.assertThat(menuById).isEqualTo(MenuTestData.sushiRestaurantMenuDto);
    }

    @Test
    void getMenuById_MenuNotFound_ThrowException() {
        Mockito.when(menuRepository.findByIdWithDishes(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(MenuNotFoundException.class)
                .isThrownBy(() -> menuService.getMenuByIdWithDishes(TestUtils.NOT_FOUND))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getMenusByRestaurant_AllOk_ReturnListOfMenus() {
        Mockito.when(menuRepository.findByRestaurantId(Mockito.anyInt(), Mockito.any()))
                .thenReturn(List.of(MenuTestData.getGetSushiRestaurantMenu()));

        List<MenuDto> menusByRestaurant = menuService.getMenusByRestaurant(RestaurantTestData.SUSHI_ID);

        Assertions.assertThat(menusByRestaurant).hasSameElementsAs(List.of(MenuTestData.sushiRestaurantMenuDto));
    }
}
