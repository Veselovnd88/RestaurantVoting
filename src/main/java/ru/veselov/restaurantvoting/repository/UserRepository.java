package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.veselov.restaurantvoting.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);
}
