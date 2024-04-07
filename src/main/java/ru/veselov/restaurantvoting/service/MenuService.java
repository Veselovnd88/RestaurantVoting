package ru.veselov.restaurantvoting.service;

import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.model.Menu;

import java.util.List;

public interface MenuService {

    MenuDto create(int restaurantId, NewMenuDto newMenuDto);

    MenuDto update(int id, NewMenuDto menuDto);

    MenuDto getMenuByIdWithDishesAndVotes(int id);

    List<MenuDto> getMenusByRestaurant(int restaurantId);

    void deleteMenu(int id);

    Menu findMenuById(int id);
}
