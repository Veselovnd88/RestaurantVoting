package ru.veselov.restaurantvoting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewRestaurantDto {

    @NotBlank
    @Size(min = 5, max = 125)
    private String name;
}
