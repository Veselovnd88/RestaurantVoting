package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.veselov.restaurantvoting.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
