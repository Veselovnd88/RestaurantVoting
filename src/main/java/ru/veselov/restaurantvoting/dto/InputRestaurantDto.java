package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;
import ru.veselov.restaurantvoting.util.HasId;

public record InputRestaurantDto(

        @Nullable
        Integer id,

        @NotBlank
        @Size(min = 5, max = 125)
        String name) implements HasId {

    @JsonCreator //constructor need int for Jackson mapping
    public InputRestaurantDto(@JsonProperty("id") Integer id,
                              @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
