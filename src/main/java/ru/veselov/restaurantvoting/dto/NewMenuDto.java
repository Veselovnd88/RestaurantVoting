package ru.veselov.restaurantvoting.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public record NewMenuDto(

        @Nullable
        Integer id,

        @NotNull
        LocalDate addedAt,
        @NotEmpty
        @Size(max = 5)
        List<@Valid DishDto> dishes) {
}
