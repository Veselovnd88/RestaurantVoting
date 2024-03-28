package ru.veselov.restaurantvoting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

public record DishDto(
        @Schema(description = "Dish id", defaultValue = "1000000")
        @Nullable
        Integer id,

        @Schema(description = "Dish name", defaultValue = "salmon sushi")
        @NotBlank
        @Size(min = 2, max = 255)
        String name,

        @Schema(description = "Dish price", defaultValue = "100000")
        @Range(min = 100, max = 100000)
        int price) {
}
