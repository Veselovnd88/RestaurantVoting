package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE r.id =:id AND m.addedAt= :date")
    Optional<Restaurant> findByIdWithMenuByDate(@Param("id") int id, @Param("date") LocalDate date);
}
