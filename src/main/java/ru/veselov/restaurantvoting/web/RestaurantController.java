package ru.veselov.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurantWithWeeklyRatingAndMenus(@PathVariable("id") Integer id) {
        return restaurantService.findByIdWithMenuAndVotesBetweenDates(id, LocalDate.now().minusDays(7), LocalDate.now());
    }
}
