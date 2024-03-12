package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.veselov.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @EntityGraph(value = Menu.DISHES_ENTITY_GRAPH)
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId AND m.addedAt>=:from AND m.addedAt<=:to")
    List<Menu> findAllBetweenDatesByRestaurant(@Param("restaurantId") Integer restaurantId,
                                              @Param("from") LocalDate from, @Param("to") LocalDate to);
}
