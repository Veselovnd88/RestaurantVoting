package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.veselov.restaurantvoting.util.HasId;

public record UserDto(

        @JsonProperty("id")
        Integer id,

        @JsonProperty("name")
        String name,

        @JsonProperty("email")
        String email
) implements HasId {

    @Override
    public Integer getId() {
        return id;
    }
}
