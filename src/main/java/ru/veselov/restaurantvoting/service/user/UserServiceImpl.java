package ru.veselov.restaurantvoting.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserDto create(UserDto userDto) {
        return null;
    }

    @Override
    public void update(UserDto userDto) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void enable(int id, boolean enabled) {

    }

    @Override
    public UserDto getById(int id) {
        return null;
    }

    @Override
    public UserDto getByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> getAll() {
        return List.of();
    }
}
