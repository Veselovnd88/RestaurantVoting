package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.veselov.restaurantvoting.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}
