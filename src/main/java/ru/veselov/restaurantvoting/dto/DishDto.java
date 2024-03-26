package ru.veselov.restaurantvoting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {

    private Integer id;

    private String name;

    private Integer price;
}