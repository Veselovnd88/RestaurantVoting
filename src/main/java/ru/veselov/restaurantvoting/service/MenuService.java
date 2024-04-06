package ru.veselov.restaurantvoting.service;

import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;

import java.util.List;

public interface MenuService {

    MenuDto create(int restaurantId, NewMenuDto newMenuDto);

    void update(int id, NewMenuDto menuDto);

    MenuDto getMenuById(int id);

    List<MenuDto> getMenusByRestaurant(int restaurantId);

    void deleteMenu(int id);
}
