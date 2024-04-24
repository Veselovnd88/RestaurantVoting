package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        LocalDate addedAt,

        @NotEmpty
        @Size(min = 2, max = 5)
        @DifferentDishes
        @JsonProperty("dishes")
        List<@Valid DishDto> dishes
) implements HasId {

    @Override
    public Integer getId() {
        return this.id;
    }
}
