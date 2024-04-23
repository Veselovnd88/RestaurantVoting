package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InputRestaurantDto(
        @NotBlank
        @Size(min = 5, max = 125)
        String name) {

    @JsonCreator //constructor need int for Jackson mapping
    public InputRestaurantDto(@JsonProperty("name") String name) {
        this.name = name;
    }
}
