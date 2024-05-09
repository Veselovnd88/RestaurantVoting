package ru.veselov.restaurantvoting.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id= :userId AND v.votedAt= :date")
    Optional<Vote> findByUserIdForDate(@Param("userId") int userId, @Param("date") LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.user.id= :userId")
    List<Vote> findAllByUserId(@Param("userId") int userId, Sort sort);
}
