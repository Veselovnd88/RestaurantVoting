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

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {
    public static final String VOTE_AFTER_LIMIT = "User [id: %s] attempt to vote after %s, current time: %s";
    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final MenuService menuService;
    private final Clock clock;

    @Value("${vote.limit-time}")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime limitTime;

    @Override
    @Transactional
    public void vote(int userId, int menuId, LocalDate localDate) {
        Optional<Vote> voteOptional = repository.findByUserIdForDate(userId, localDate);
        if (voteOptional.isPresent()) {
            checkVoteTimeExceedsLimit(userId);
            Menu menu = menuService.findMenuById(menuId);
            Vote vote = voteOptional.get();
            vote.setMenu(menu);
            repository.save(vote);
            log.info("User [id: {}] changes his mind and re-voted for menu id: {}", userId, menuId);
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User with id: %s not found".formatted(userId)));
            Menu menu = menuService.findMenuById(menuId);
            Vote vote = new Vote(user, menu, LocalDate.now(clock));
            repository.save(vote);
            log.info("User [id: {}] voted for menu [id: {}]", userId, menuId);
        }
    }

    @Override
    @Transactional
    public void removeVote(int userId, LocalDate localDate) {
        checkVoteTimeExceedsLimit(userId);
        repository.deleteByUserIdForDate(userId, localDate);
        log.info("User [id: {}] decline his vote at [{}]", userId, localDate);
    }

    private void checkVoteTimeExceedsLimit(int userId) {
        LocalTime now = LocalTime.now(clock);
        if (now.isAfter(limitTime)) {
            log.warn(VOTE_AFTER_LIMIT.formatted(userId, limitTime,now ));
            throw new VotingTimeLimitExceedsException(VOTE_AFTER_LIMIT.formatted(userId, limitTime, now));
        }
    }
}
