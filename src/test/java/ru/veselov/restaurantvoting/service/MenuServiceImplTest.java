package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
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
import ru.veselov.restaurantvoting.exception.MenuConflictException;
import ru.veselov.restaurantvoting.exception.ObjectAlreadyExistsException;
import ru.veselov.restaurantvoting.exception.RestaurantNotFoundException;
import ru.veselov.restaurantvoting.mapper.DishMapper;
import ru.veselov.restaurantvoting.mapper.DishMapperImpl;
import ru.veselov.restaurantvoting.mapper.MenuMapper;
import ru.veselov.restaurantvoting.mapper.MenuMapperImpl;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Mockito.when(menuRepository.existsByRestaurantIdAndDate(Mockito.anyInt(), Mockito.any())).thenReturn(false);

        menuService.create(RestaurantTestData.SUSHI_ID, MenuTestData.menuDtoToCreate);

        Mockito.verify(menuRepository, Mockito.times(1)).save(menuArgumentCaptor.capture());
        Menu captured = menuArgumentCaptor.getValue();
        Assertions.assertThat(captured).extracting(Menu::getAddedAt, Menu::getRestaurant)
                .contains(MenuTestData.menuDtoToCreate.addedAt(), RestaurantTestData.sushiRestaurant);
        DishTestData.DISH_MATCHER.assertMatch(captured.getDishes(), DishTestData.tastyDishEntity);
    }

    @Test
    void create_MenuForThisDateAlreadyExists_ThrowException() {
        Mockito.when(restaurantRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(RestaurantTestData.sushiRestaurant));
        Mockito.when(menuRepository.existsByRestaurantIdAndDate(Mockito.anyInt(), Mockito.any())).thenReturn(true);

        Assertions.assertThatExceptionOfType(MenuConflictException.class).isThrownBy(
                        () -> menuService.create(RestaurantTestData.SUSHI_ID, MenuTestData.menuDtoToCreateForConflict))
                .isInstanceOf(ObjectAlreadyExistsException.class)
                .withMessage(MenuConflictException.MESSAGE_CONFLICT
                        .formatted(RestaurantTestData.SUSHI_ID, MenuTestData.ADDED_DATE));
        Mockito.verify(menuRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void create_NoRestaurant_ThrowException() {
        Mockito.when(restaurantRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RestaurantNotFoundException.class)
                .isThrownBy(() -> menuService.create(RestaurantTestData.SUSHI_ID, MenuTestData.menuDtoToCreate))
                .isInstanceOf(EntityNotFoundException.class)
                .withMessage(RestaurantNotFoundException.MESSAGE_WITH_ID.formatted(RestaurantTestData.SUSHI_ID));
        Mockito.verifyNoInteractions(menuRepository);
    }

    @Test
    void update_AllOk_UpdateMenuAndReturnUpdated() {
        Mockito.when(menuRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(MenuTestData.getSushiRestaurantMenu()));

        menuService.update(MenuTestData.SUSHI_MENU_ID, MenuTestData.menuDtoToCreate);

        Mockito.verify(menuRepository, Mockito.times(1)).save(menuArgumentCaptor.capture());
        Menu captured = menuArgumentCaptor.getValue();
        Assertions.assertThat(captured).extracting(Menu::getAddedAt).isEqualTo(MenuTestData.ADDED_DATE.plusDays(1));
        DishTestData.DISH_MATCHER.assertMatch(captured.getDishes(), Set.of(DishTestData.tastyDishEntity));
    }

    @Test
    void update_MenuNotFound_ThrowException() {
        Mockito.when(menuRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> menuService.update(MenuTestData.SUSHI_MENU_ID, MenuTestData.menuDtoToCreate))
                .isInstanceOf(EntityNotFoundException.class);
        Mockito.verify(menuRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void getMenuById_AllOk_ReturnMenuDtoWithDishesAndVotes() {
        Mockito.when(menuRepository.findByIdWithDishesAndVotes(Mockito.anyInt())).thenReturn(Optional.of(MenuTestData.sushiRestaurantMenu));

        MenuDto menuById = menuService.getMenuByIdWithDishesAndVotes(MenuTestData.SUSHI_MENU_ID);

        Assertions.assertThat(menuById).isEqualTo(MenuTestData.sushiRestaurantMenuDto);
    }

    @Test
    void getMenuById_MenuNotFound_ThrowExceptionWithDishesAndVotes() {
        Mockito.when(menuRepository.findByIdWithDishesAndVotes(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> menuService.getMenuByIdWithDishesAndVotes(MenuTestData.SUSHI_MENU_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getMenusByRestaurant_AllOk_ReturnListOfMenus() {
        Mockito.when(menuRepository.findByRestaurantId(Mockito.anyInt(), Mockito.any()))
                .thenReturn(List.of(MenuTestData.sushiRestaurantMenu));

        List<MenuDto> menusByRestaurant = menuService.getMenusByRestaurant(RestaurantTestData.SUSHI_ID);

        Assertions.assertThat(menusByRestaurant).hasSameElementsAs(List.of(MenuTestData.sushiRestaurantMenuDto));
    }
}