package ru.veselov.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo {

    private Integer id;

    private String name;

    private Integer voteCount;
}