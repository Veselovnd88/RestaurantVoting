package ru.veselov.restaurantvoting.service;

import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {

    RestaurantDto create(NewRestaurantDto restaurantDto);

    List<RestaurantDto> getAll();

    RestaurantDto findByIdWithMenuAndVotesBetweenDates(int id, LocalDate from, LocalDate to);

    RestaurantDto update(int id, NewRestaurantDto restaurantDto);

    void delete(int id);
}
