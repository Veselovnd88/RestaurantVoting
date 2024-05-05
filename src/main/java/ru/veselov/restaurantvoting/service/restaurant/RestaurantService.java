package ru.veselov.restaurantvoting.service.restaurant;

import ru.veselov.restaurantvoting.dto.InputRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {

    RestaurantDto create(InputRestaurantDto restaurantDto);

    List<RestaurantDto> getAll();

    RestaurantDto findByIdWithMenuForDate(int id, LocalDate date);

    RestaurantDto findById(int id);

    RestaurantDto update(int id, InputRestaurantDto restaurantDto);

    void delete(int id);

    List<RestaurantDto> findAllWithMenuByDate(LocalDate date);
}
