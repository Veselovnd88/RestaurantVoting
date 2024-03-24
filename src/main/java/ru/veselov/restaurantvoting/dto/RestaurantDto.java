package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDto {

    private Integer id;
    private String name;
    private List<MenuDto> menus;
    private Integer voteCount;

    public RestaurantDto(Integer id, String name, Integer voteCount) {
        this.id = id;
        this.name = name;
        this.voteCount = voteCount;
    }
}
