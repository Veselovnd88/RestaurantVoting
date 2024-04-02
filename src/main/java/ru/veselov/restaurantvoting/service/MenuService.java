package ru.veselov.restaurantvoting.service;

import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    void addMenu(int restaurantId, LocalDate localDate, NewMenuDto menuDto);

    void updateMenu(int id, NewMenuDto menuDto);

    MenuDto getMenuById(int id);

    List<MenuDto> getMenusByRestaurant(int restaurantId);

    void deleteMenu(int id);
}
