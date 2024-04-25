package ru.veselov.restaurantvoting.service.dish;

import ru.veselov.restaurantvoting.dto.DishDto;

import java.util.List;

public interface DishService {

    DishDto save(int menuId, DishDto dishDto);

    DishDto update(int id, DishDto dishDto);

    void delete(int id);

    DishDto findOne(int id);

    List<DishDto> findAll();

    List<DishDto> findAllByRestaurantId(int id);
}
