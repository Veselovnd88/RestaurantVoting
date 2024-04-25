package ru.veselov.restaurantvoting.service.vote;

import java.time.LocalDate;

public interface VoteService {

    void vote(int userId, int restaurantId, LocalDate localDate);

    void removeVote(int userId, LocalDate localDate);
}
