package ru.veselov.restaurantvoting.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
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
@Slf4j
public class UserServiceImpl implements UserService {

    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create new user
     *
     * @param userDto new userDto
     * @return {@link UserDto} saved userDto
     * @throws org.springframework.dao.DataIntegrityViolationException if email duplicated
     */
    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = mapper.toUser(userDto);
        user.setRoles(Set.of(Role.USER));
        user.setPassword(passwordEncoder.encode(userDto.password()));
        User saved = repository.save(user);
        log.info("User with email: {} saved to repository", saved.getEmail());
        return mapper.toUserDto(saved);
    }

    /**
     * Update user
     *
     * @param userDto new userDto
     * @throws org.springframework.dao.DataIntegrityViolationException if email duplicated
     */
    @Override
    @Transactional
    public void update(UserDto userDto) {
        User user = getUserById(userDto.id());
        mapper.toUserUpdate(user, userDto);
        user.setPassword(StringUtils.isBlank(userDto.password()) ? user.getPassword()
                : passwordEncoder.encode(userDto.password()));
        User updated = repository.save(user);
        log.info("User with email: {} updated", updated.getEmail());
    }

    /**
     * Delete user by id
     *
     * @param id id of user to delete
     */
    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
        log.info("User with id: {} deleted", id);
    }

    /**
     * Change user's status enable/disable
     *
     * @param id      id of user
     * @param enabled flag for enable/diable
     * @throws UserNotFoundException if user not found by id
     */
    @Override
    @Transactional
    public void enable(int id, boolean enabled) {
        User userById = getUserById(id);
        userById.setEnabled(enabled);
        repository.save(userById);
        log.info("User with id: {} change enable status to {}", id, enabled);
    }

    /**
     * Get user by id
     *
     * @param id id of user
     * @throws UserNotFoundException if user not found by id
     */
    @Override
    public UserDto getById(int id) {
        User userById = getUserById(id);
        log.info("User with id: {} found", id);
        return mapper.toUserDto(userById);
    }

    /**
     * Get user by email
     *
     * @param email email of user
     * @throws UserNotFoundException of user not found by email
     */
    @Override
    public UserDto getByEmail(String email) {
        User user = repository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        log.info("User with email: {} found", email);
        return mapper.toUserDto(user);
    }

    /**
     * Get all users
     */
    @Override
    public List<UserDto> getAll() {
        List<User> users = repository.findAll(SORT_NAME_EMAIL);
        log.info("Users found");
        return mapper.toDtos(users);
    }

    private User getUserById(int id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
