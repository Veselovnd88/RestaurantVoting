package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;
import ru.veselov.restaurantvoting.util.HasId;
import ru.veselov.restaurantvoting.web.validaton.DifferentDishes;

import java.time.LocalDate;
import java.util.List;

public record InputMenuDto(

        @Nullable
        @JsonProperty("id")
        Integer id,

        @NotNull
        @JsonProperty("addedAt")
        @Schema(description = "Day of menu added", defaultValue = "2024-04-26")
        LocalDate addedAt,

        @NotEmpty
        @Size(min = 2, max = 5)
        @DifferentDishes
        @JsonProperty("dishes")
        @ArraySchema(schema = @Schema(implementation = DishDto.class, maximum = "5", minimum = "2",
                description = "Dishes to add"))
        List<@Valid DishDto> dishes
) implements HasId {

    @Override
    public Integer getId() {
        return this.id;
    }
}
