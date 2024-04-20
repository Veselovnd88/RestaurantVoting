package ru.veselov.restaurantvoting.service;

import org.springframework.lang.NonNull;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    MenuDto create(int restaurantId, NewMenuDto newMenuDto);

    MenuDto update(int id, NewMenuDto menuDto);

    MenuDto getMenuByIdWithDishesAndVotes(int id);

    List<MenuDto> getMenusByRestaurant(int restaurantId);

    void deleteMenu(int id);

    @NonNull
    Menu findMenuById(int id);

    Menu findMenuByRestaurantIdAndLocalDate(int restaurantId, LocalDate localDate);
}
