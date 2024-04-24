package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.veselov.restaurantvoting.util.HasId;

import java.util.List;

public record RestaurantDto(

        @JsonProperty("id")
        Integer id,

        @JsonProperty("name")
        String name,

        @JsonProperty("menus")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<MenuDto> menus)
        implements HasId {

    @Override
    public Integer getId() {
        return id();
    }
}
