package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import ru.veselov.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @EntityGraph(attributePaths = {"votes", "dishes"})
    @NonNull
    Optional<Menu> findById(@NonNull Integer id);

    @EntityGraph(value = Menu.GRAPH_DISHES_VOTES_USERS)
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId AND m.addedAt=:date")
    Optional<Menu> findByRestaurantIdByDate(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);
}