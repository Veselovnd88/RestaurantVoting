package ru.veselov.restaurantvoting.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record NewMenuDto(
        int id,
        @NotEmpty
        @Size(max = 5)
        List<@Valid DishDto> dishes) {
}
