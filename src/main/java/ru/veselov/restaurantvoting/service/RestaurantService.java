package ru.veselov.restaurantvoting.service;

import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {

    RestaurantDto create(NewRestaurantDto restaurantDto);

    List<RestaurantDto> getAll();

    RestaurantDto findByIdWithMenuAndVotesForDate(int id, LocalDate date);

    RestaurantDto findById(int id);

    RestaurantDto update(int id, NewRestaurantDto restaurantDto);

    void delete(int id);
}
