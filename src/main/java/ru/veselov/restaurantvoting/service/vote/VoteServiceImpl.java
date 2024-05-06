package ru.veselov.restaurantvoting.service.vote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.VoteDto;
import ru.veselov.restaurantvoting.exception.UserNotFoundException;
import ru.veselov.restaurantvoting.exception.VotingTimeLimitExceedsException;
import ru.veselov.restaurantvoting.mapper.VoteMapper;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.User;
import ru.veselov.restaurantvoting.model.Vote;
import ru.veselov.restaurantvoting.repository.UserRepository;
import ru.veselov.restaurantvoting.repository.VoteRepository;
import ru.veselov.restaurantvoting.service.menu.MenuService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {

    public static final Sort SORT_BY_DATE_DESC = Sort.by(Sort.Direction.DESC, "votedAt");

    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final MenuService menuService;
    private final VoteMapper voteMapper;

    @Value("${vote.limit-time}")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime limitTime;

    /**
     * User vote for restaurant for date
     *
     * @param userId        user id
     * @param restaurantId  restaurant id
     * @param localDateTime date for vote
     * @throws VotingTimeLimitExceedsException if vote time exceed limit
     */
    @Override
    @Transactional
    public void vote(int userId, int restaurantId, LocalDateTime localDateTime) {
        Optional<Vote> voteOptional = repository.findByUserIdForDate(userId, localDateTime.toLocalDate());
        if (voteOptional.isPresent()) {
            checkVoteTimeExceedsLimit(userId, localDateTime);
            Menu menu = menuService.findMenuByRestaurantIdAndLocalDate(restaurantId, localDateTime.toLocalDate());
            Vote vote = voteOptional.get();
            vote.setMenu(menu);
            repository.save(vote);
            log.info("User [id: {}] changes his mind and re-voted for menu id: {}", userId, restaurantId);
        } else {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
            Menu menu = menuService.findMenuByRestaurantIdAndLocalDate(restaurantId, localDateTime.toLocalDate());
            Vote vote = new Vote(user, menu, localDateTime.toLocalDate());
            repository.save(vote);
            log.info("User [id: {}] voted for menu [id: {}]", userId, restaurantId);
        }
    }

    /**
     * Get user vote for date
     *
     * @param userId    user id
     * @param localDate date
     * @return Optional<VoteDto> optional for VoteDto
     */
    @Override
    public Optional<VoteDto> getByUserIdForDate(int userId, LocalDate localDate) {
        Optional<Vote> optionalVote = repository.findByUserIdForDate(userId, localDate);
        if (optionalVote.isPresent()) {
            Vote vote = optionalVote.get();
            log.info("User [id: {}] vote at [{}]", userId, localDate);
            return Optional.of(voteMapper.toDto(vote));
        } else return Optional.empty();
    }

    /**
     * Get all user's votes
     *
     * @param userId user id
     * @return List<VoteDto> vote dtos
     */
    @Override
    public List<VoteDto> getAllByUserId(int userId) {
        return voteMapper.toDtos(repository.findAllByUserId(userId, SORT_BY_DATE_DESC));
    }

    private void checkVoteTimeExceedsLimit(int userId, LocalDateTime localDateTime) {
        if (localDateTime.toLocalTime().isAfter(limitTime)) {
            throw new VotingTimeLimitExceedsException(userId, limitTime, localDateTime.toLocalTime());
        }
    }
}
