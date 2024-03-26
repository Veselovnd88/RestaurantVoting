package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.veselov.restaurantvoting.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {


}
