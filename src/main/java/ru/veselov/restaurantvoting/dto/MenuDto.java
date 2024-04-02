package ru.veselov.restaurantvoting.dto;

import java.time.LocalDate;
import java.util.List;


public record MenuDto(

        Integer id,

        LocalDate addedAt,

        List<DishDto> dishes,

        List<VoteDto> votes) {
}
