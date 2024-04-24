package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.veselov.restaurantvoting.util.HasId;

import java.util.List;

public record RestaurantDto(

        Integer id,
        String name,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<MenuDto> menus) implements HasId {

    @JsonCreator
    public RestaurantDto(@JsonProperty("id") Integer id,
                         @JsonProperty("name") String name,
                         @JsonProperty("menus") List<MenuDto> menus) {
        this.id = id;
        this.name = name;
        this.menus = menus;
    }

    @Override
    public Integer getId() {
        return id();
    }
}
