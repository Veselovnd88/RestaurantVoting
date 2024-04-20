package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;
import ru.veselov.restaurantvoting.web.validaton.DifferentDishes;

import java.time.LocalDate;
import java.util.List;

public record NewMenuDto(

        @Nullable
        Integer id,

        @NotNull
        LocalDate addedAt,

        @NotEmpty
        @Size(max = 5)
        @DifferentDishes
        List<@Valid DishDto> dishes) {

    @JsonCreator //constructor need int for Jackson mapping
    public NewMenuDto(@JsonProperty("id") Integer id, @JsonProperty("addedAt") LocalDate addedAt,
                      @JsonProperty("dishes") List<DishDto> dishes) {
        this.id = id;
        this.addedAt = addedAt;
        this.dishes = dishes;
    }
}
