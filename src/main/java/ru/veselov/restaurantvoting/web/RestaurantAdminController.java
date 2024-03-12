package ru.veselov.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.service.RestaurantService;

@RestController
@RequestMapping("/api/v1/admin/restaurant")
@RequiredArgsConstructor
public class RestaurantAdminController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantDto create(@RequestBody NewRestaurantDto restaurantDto) {
        return restaurantService.create(restaurantDto);
    }

    @PutMapping
    public RestaurantDto update(){
        return null;
    }
}
