package ru.veselov.restaurantvoting.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.exception.UserNotFoundException;
import ru.veselov.restaurantvoting.mapper.UserMapper;
import ru.veselov.restaurantvoting.model.Role;
import ru.veselov.restaurantvoting.model.User;
import ru.veselov.restaurantvoting.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        User user = mapper.toUser(userDto);
        user.setRoles(Set.of(Role.USER));
        user.setPassword(passwordEncoder.encode(userDto.password()));
        User saved = repository.save(user);
        log.info("User with email: {} saved to repository", saved.getEmail());
        return mapper.toUserDto(saved);
    }

    @Transactional
    @Override
    public void update(UserDto userDto) {
        User user = getUserById(userDto.id());
        mapper.toUserUpdate(user, userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User updated = repository.save(user);
        log.info("User with email: {} updated", updated.getEmail());
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
        log.info("User with id: {} deleted", id);
    }

    @Transactional
    @Override
    public void enable(int id, boolean enabled) {
        User userById = getUserById(id);
        userById.setEnabled(enabled);
        repository.save(userById);
        log.info("User with id: {} change enable status to {}", id, enabled);
    }

    @Override
    public UserDto getById(int id) {
        User userById = getUserById(id);
        log.info("User with id: {} found", id);
        return mapper.toUserDto(userById);
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = repository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        log.info("User with email: {} found", email);
        return mapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = repository.findAll();
        log.info("Users found");
        return mapper.toDtos(users);
    }

    private User getUserById(int id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
