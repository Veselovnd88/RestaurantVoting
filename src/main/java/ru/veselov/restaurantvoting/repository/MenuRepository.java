package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.veselov.restaurantvoting.model.Menu;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @EntityGraph(attributePaths = {"votes", "dishes"})
    @NonNull
    Optional<Menu> findById(@NonNull Integer id);
}
