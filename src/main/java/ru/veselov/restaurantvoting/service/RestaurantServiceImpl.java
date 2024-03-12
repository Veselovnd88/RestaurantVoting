package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.mapper.RestaurantMapper;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;
import ru.veselov.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    public static final Sort SORT_BY_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;
    private final VoteRepository voteRepository;
    private final MenuRepository menuRepository;

    public List<RestaurantDto> getAll() {
        log.debug("Retrieving restaurants with votes from repository");
        return mapper.entitiesToDto(repository.findAll(SORT_BY_NAME));
    }

    public RestaurantDto findByIdWithMenuAndVotesBetweenDates(int id, LocalDate from, LocalDate to) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with [id:%s] not found".formatted(id)));
        restaurant.setVotes(voteRepository.findAllBetweenDatesByRestaurant(id, from, to));
        restaurant.setMenus(menuRepository.findAllBetweenDatesByRestaurant(id, from, to));
        log.debug("Retrieving restaurant id: {} with votes and menu between dates: {} | {}", id, from, to);
        return mapper.entityToDto(restaurant);
    }
}
