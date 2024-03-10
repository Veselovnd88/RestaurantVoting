package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.veselov.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.votedAt>=:from AND v.votedAt<=:to")
    List<Vote> findAllBetweenDatesByRestaurant(@Param("restaurantId") Integer restaurantId,
                                               @Param("from") LocalDate from, @Param("to") LocalDate to);
}
