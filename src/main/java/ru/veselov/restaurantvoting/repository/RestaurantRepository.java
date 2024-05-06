package ru.veselov.restaurantvoting.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menus m " +
            "LEFT JOIN FETCH m.dishes " +
            "WHERE r.id =:id AND m.date= :date")
    Optional<Restaurant> findByIdWithMenuByDate(@Param("id") int id, @Param("date") LocalDate date);

    @Query("SELECT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menus m " +
            "LEFT JOIN FETCH m.dishes " +
            "WHERE m.date= :date")
    List<Restaurant> findAllWithMenuByDate(@Param("date") LocalDate date, Sort sort);
}
