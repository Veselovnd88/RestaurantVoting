package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import ru.veselov.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @NonNull
    List<Restaurant> findAll();

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE m.addedAt=:addedAt")
    List<Restaurant> findAllWithMenuByDate(@Param("addedAt") LocalDate addedAt);

    @Query("SELECT r FROM  Restaurant r LEFT JOIN r.menus m WHERE r.id=:id AND m.addedAt=:addedAt")
    Optional<Restaurant> findByIdWithMenuAndVotes(@Param("id") Integer id,
                                                  @Param("addedAt") LocalDate addedAt);
}
