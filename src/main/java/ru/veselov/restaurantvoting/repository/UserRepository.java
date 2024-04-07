package ru.veselov.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.veselov.restaurantvoting.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
