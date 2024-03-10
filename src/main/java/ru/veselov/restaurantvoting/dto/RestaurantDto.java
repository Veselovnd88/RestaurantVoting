package ru.veselov.restaurantvoting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {

    private Integer id;

    private String name;

    private Integer voteCount;
}