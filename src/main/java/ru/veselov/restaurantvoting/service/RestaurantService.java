package ru.veselov.restaurantvoting.service;

import ru.veselov.restaurantvoting.dto.RestaurantDto;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {

    List<RestaurantDto> getAll();

    RestaurantDto findByIdWithMenuAndVotesBetweenDates(int id, LocalDate from, LocalDate to);
}
