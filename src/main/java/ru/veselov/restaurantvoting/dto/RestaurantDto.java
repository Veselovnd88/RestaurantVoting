package ru.veselov.restaurantvoting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {

    private Integer id;

    private String name;

    private List<MenuDto> menus;

    private Integer voteCount;
}