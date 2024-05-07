package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import ru.veselov.restaurantvoting.util.HasId;

import java.time.LocalDate;

public record InputMenuDto(

        @Nullable
        @JsonProperty("id")
        Integer id,

        @NotNull
        @JsonProperty("date")
        @Schema(description = "Day of menu added", defaultValue = "2024-04-26")
        LocalDate date

/*        @NotEmpty
        @Size(min = 2, max = 5)
        @DifferentDishes
        @JsonProperty("dishes")
        @ArraySchema(schema = @Schema(implementation = DishDto.class, maximum = "5", minimum = "2",
                description = "Dishes to add"))
        List<@Valid DishDto> dishes*/
) implements HasId {

    @Override
    public Integer getId() {
        return this.id;
    }
}
