package ru.veselov.restaurantvoting.service;

import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.dto.NewDishDto;

import java.util.List;

public interface DishService {

    DishDto save(NewDishDto dishDto);

    DishDto update(int id, NewDishDto dishDto);

    void delete(int id);

    DishDto findOne(int id);

    List<DishDto> findAll();
}
