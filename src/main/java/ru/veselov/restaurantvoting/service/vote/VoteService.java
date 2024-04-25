package ru.veselov.restaurantvoting.service.vote;

import ru.veselov.restaurantvoting.dto.VoteDto;

import java.time.LocalDate;
import java.util.Optional;

public interface VoteService {

    void vote(int userId, int restaurantId, LocalDate localDate);

    void removeVote(int userId, LocalDate localDate);

    Optional<VoteDto> getByUserIdForDate(int userId, LocalDate localDate);
}
