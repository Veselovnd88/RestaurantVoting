package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.exception.VotingTimeLimitExceedsException;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.User;
import ru.veselov.restaurantvoting.model.Vote;
import ru.veselov.restaurantvoting.repository.UserRepository;
import ru.veselov.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {
    public static final String VOTE_AFTER_LIMIT = "Attempt to vote after %s";
    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final MenuService menuService;

    @Value("${vote.limit-time}")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime limitTime;

    @Override
    @Transactional
    public void vote(int userId, int menuId, LocalDate localDate) {
        Optional<Vote> voteOptional = repository.findByUserIdForToday(userId, localDate);
        Menu menu = menuService.findMenuById(menuId);
        if (voteOptional.isPresent()) {
            Vote vote = voteOptional.get();
            if (LocalTime.now().isBefore(limitTime)) {
                vote.setMenu(menu);
                repository.save(vote);
                log.info("User id: {} changes his mind and re-voted for menu id: {}", userId, menuId);
            } else {
                log.warn(VOTE_AFTER_LIMIT.formatted(limitTime));
                throw new VotingTimeLimitExceedsException(VOTE_AFTER_LIMIT.formatted(limitTime));
            }
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User with id: %s not found".formatted(userId)));
            Vote vote = new Vote(user, menu);
            repository.save(vote);
            log.info("User id: {} voted for menu id: {}", userId, menuId);
        }
    }
}
