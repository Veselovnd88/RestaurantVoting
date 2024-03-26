package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.mapper.RestaurantMapper;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;
import ru.veselov.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    public static final Sort SORT_BY_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public RestaurantDto create(NewRestaurantDto restaurantDto) {
        Restaurant savedRestaurant = repository.save(mapper.toEntity(restaurantDto));
        log.info("New restaurant: {} successfully saved", savedRestaurant.getName());
        return mapper.entityToDtoWithMenus(savedRestaurant);
    }

    public List<RestaurantDto> getAll() {
        log.debug("Retrieving restaurants with votes from repository");
        return mapper.entitiesToDto(repository.findAll(SORT_BY_NAME));
    }

    public RestaurantDto findById(int id) {
        log.debug("Retrieving restaurant by id");
        return mapper.entityToDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with [id:%s] not found".formatted(id))));
    }

    public RestaurantDto findByIdWithMenuAndVotesForDate(int id, LocalDate date) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with [id:%s] not found".formatted(id)));
        Optional<Menu> byRestaurantIdByDate = menuRepository.findByRestaurantIdByDate(id, date);
        restaurant.setMenus(byRestaurantIdByDate.map(List::of).orElse(Collections.emptyList()));
        log.debug("Retrieving restaurant id: {} with votes and menu by date {}", id, date);
        return mapper.entityToDtoWithMenus(restaurant);
    }

    @Override
    @Transactional
    public RestaurantDto update(int id, NewRestaurantDto restaurantDto) {
        Restaurant restaurant = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Restaurant with id: %s not found".formatted(id)));
        restaurant.setName(restaurantDto.getName());
        Restaurant updatedRestaurant = repository.save(restaurant);
        log.info("Restaurant with id: {} updated", id);
        return mapper.entityToDto(updatedRestaurant);
    }

    @Override
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
        log.info("Restaurant with id: {} deleted", id);
    }
}
