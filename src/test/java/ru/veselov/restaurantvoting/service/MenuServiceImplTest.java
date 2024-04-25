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
import ru.veselov.restaurantvoting.exception.MenuNotFoundException;
import ru.veselov.restaurantvoting.exception.RestaurantNotFoundException;
import ru.veselov.restaurantvoting.mapper.DishMapper;
import ru.veselov.restaurantvoting.mapper.DishMapperImpl;
import ru.veselov.restaurantvoting.mapper.MenuMapper;
import ru.veselov.restaurantvoting.mapper.MenuMapperImpl;
import ru.veselov.restaurantvoting.mapper.UserMapper;
import ru.veselov.restaurantvoting.mapper.UserMapperImpl;
import ru.veselov.restaurantvoting.mapper.VoteMapper;
import ru.veselov.restaurantvoting.mapper.VoteMapperImpl;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;
import ru.veselov.restaurantvoting.util.DishTestData;
import ru.veselov.restaurantvoting.util.MenuTestData;
import ru.veselov.restaurantvoting.util.RestaurantTestData;

import java.time.LocalDate;
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
        VoteMapperImpl voteMapper = new VoteMapperImpl();
        ReflectionTestUtils.setField(voteMapper, "userMapper", new UserMapperImpl(), UserMapper.class);
        ReflectionTestUtils.setField(menuMapper, "dishMapper", new DishMapperImpl(), DishMapper.class);
        ReflectionTestUtils.setField(menuMapper, "voteMapper", voteMapper, VoteMapper.class);
        ReflectionTestUtils.setField(menuService, "mapper", menuMapper, MenuMapper.class);
    }

    @Test
    void create_AllOk_ShouldSaveNewMenuWithDishes() {
        Mockito.when(restaurantRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(RestaurantTestData.sushiRestaurant));

        menuService.create(RestaurantTestData.SUSHI_ID, MenuTestData.menuDtoToCreate);

        Mockito.verify(menuRepository, Mockito.times(1)).save(menuArgumentCaptor.capture());
        Menu captured = menuArgumentCaptor.getValue();
        Assertions.assertThat(captured).extracting(Menu::getAddedAt, Menu::getRestaurant)
                .contains(MenuTestData.menuDtoToCreate.addedAt(), RestaurantTestData.sushiRestaurant);
        DishTestData.DISH_MATCHER.assertMatch(captured.getDishes(),
                DishTestData.tastyDishEntity, DishTestData.tastyDishEntity2);
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
                .thenReturn(Optional.of(MenuTestData.getGetSushiRestaurantMenu()));

        menuService.update(MenuTestData.SUSHI_MENU_ID, MenuTestData.menuDtoToCreate);

        Mockito.verify(menuRepository, Mockito.times(1)).save(menuArgumentCaptor.capture());
        Menu captured = menuArgumentCaptor.getValue();
        Assertions.assertThat(captured).extracting(Menu::getAddedAt).isEqualTo(MenuTestData.ADDED_DATE.plusDays(1));
        DishTestData.DISH_MATCHER.assertMatch(captured.getDishes(), DishTestData.getUpdatedDishesInSortedSet());
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
        Mockito.when(menuRepository.findByIdWithDishesAndVotes(Mockito.anyInt())).thenReturn(Optional.of(MenuTestData.getSushiRestaurantMenuWithVotes()));

        MenuDto menuById = menuService.getMenuByIdWithDishesAndVotes(MenuTestData.SUSHI_MENU_ID);

        Assertions.assertThat(menuById).isEqualTo(MenuTestData.sushiRestaurantMenuDtoWithVotes);
    }

    @Test
    void getMenuById_MenuNotFound_ThrowException() {
        Mockito.when(menuRepository.findByIdWithDishesAndVotes(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> menuService.getMenuByIdWithDishesAndVotes(MenuTestData.SUSHI_MENU_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getMenusByRestaurant_AllOk_ReturnListOfMenus() {
        Mockito.when(menuRepository.findByRestaurantId(Mockito.anyInt(), Mockito.any()))
                .thenReturn(List.of(MenuTestData.getSushiRestaurantMenuWithVotes()));

        List<MenuDto> menusByRestaurant = menuService.getMenusByRestaurant(RestaurantTestData.SUSHI_ID);

        Assertions.assertThat(menusByRestaurant).hasSameElementsAs(List.of(MenuTestData.sushiRestaurantMenuDto));
    }

    @Test
    void findMenuByRestaurantIdAndLocalDate_MenuFound_ReturnMenu() {
        Mockito.when(menuRepository.findByRestaurantIdByDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(Optional.of(MenuTestData.getSushiRestaurantMenuWithVotes()));

        menuService.findMenuByRestaurantIdAndLocalDate(RestaurantTestData.SUSHI_ID, LocalDate.of(2024, 4, 20));

        Mockito.verify(menuRepository).findByRestaurantIdByDate(RestaurantTestData.SUSHI_ID, LocalDate.of(2024, 4, 20));
    }

    @Test
    void findMenuByRestaurantIdAndLocalDate_MenuNotFound_ThrowException() {
        Mockito.when(menuRepository.findByRestaurantIdByDate(Mockito.anyInt(), Mockito.any()))
                .thenReturn(Optional.empty());

        LocalDate localDate = LocalDate.of(2024, 4, 20);
        Assertions.assertThatExceptionOfType(MenuNotFoundException.class).isThrownBy(() ->
                        menuService.findMenuByRestaurantIdAndLocalDate(RestaurantTestData.SUSHI_ID, localDate))
                .withMessage(MenuNotFoundException.MESSAGE_WITH_REST_ID_FOR_DATE
                        .formatted(RestaurantTestData.SUSHI_ID, localDate))
                .isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(menuRepository).findByRestaurantIdByDate(RestaurantTestData.SUSHI_ID, localDate);
    }
}
