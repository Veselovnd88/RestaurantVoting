package ru.veselov.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {

    Integer id;

    String name;

    Integer price;
}