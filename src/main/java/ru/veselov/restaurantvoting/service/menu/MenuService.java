package ru.veselov.restaurantvoting.service.menu;

import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    MenuDto create(int restaurantId, LocalDate localDate);

    MenuDto update(int id, InputMenuDto menuDto);

    MenuDto getMenuByIdWithDishes(int id);

    List<MenuDto> getMenusByRestaurant(int restaurantId);

    void deleteMenu(int id);

    Menu findById(int id);
}
