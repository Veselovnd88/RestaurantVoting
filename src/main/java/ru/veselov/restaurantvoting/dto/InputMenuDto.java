package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
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
        Integer id,

        @NotNull
        LocalDate addedAt,

        @NotEmpty
        @Size(min = 2, max = 5)
        @DifferentDishes
        List<@Valid DishDto> dishes) implements HasId {

    @JsonCreator //constructor need int for Jackson mapping
    public InputMenuDto(@JsonProperty("id") Integer id, @JsonProperty("addedAt") LocalDate addedAt,
                        @JsonProperty("dishes") List<DishDto> dishes) {
        this.id = id;
        this.addedAt = addedAt;
        this.dishes = dishes;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
