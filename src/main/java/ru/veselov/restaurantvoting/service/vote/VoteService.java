package ru.veselov.restaurantvoting.service.vote;

import ru.veselov.restaurantvoting.dto.VoteDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoteService {

    VoteDto vote(int userId, int restaurantId, LocalDateTime localDateTime);

    void changeVote(int userId, int restaurantId, LocalDateTime localDateTime);

    Optional<VoteDto> getByUserIdForDate(int userId, LocalDate localDate);

    List<VoteDto> getAllByUserId(int userId);
}
