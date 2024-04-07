package ru.veselov.restaurantvoting.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.veselov.restaurantvoting.model.Dish;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("SELECT d FROM Dish d left JOIN d.menu m where m.restaurant.id = :id")
    List<Dish> findAllByRestaurantId(@Param("id") int id, Sort sort);
}
