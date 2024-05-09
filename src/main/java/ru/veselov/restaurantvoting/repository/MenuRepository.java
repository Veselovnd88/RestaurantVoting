package ru.veselov.restaurantvoting.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.model.Menu;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @EntityGraph(attributePaths = {"dishes"})
    @Query("SELECT m FROM Menu m WHERE m.id= :id")
    @NonNull
    Optional<Menu> findByIdWithDishes(@Param("id") int id);

    @EntityGraph(attributePaths = {"dishes"})
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId")
    List<Menu> findByRestaurantId(@Param("restaurantId") int restaurantId, Sort sort);

    Optional<Menu> findById(int id);

    @Transactional
    void deleteById(int id);
}
