package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.veselov.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id= :userId " +
            "AND v.votedAt= :date")
    Optional<Vote> findByUserIdForToday(@Param("userId") int userId,
                                        @Param("date") LocalDate date);
}
