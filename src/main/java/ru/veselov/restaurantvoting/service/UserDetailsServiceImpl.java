package ru.veselov.restaurantvoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.veselov.restaurantvoting.model.User;
import ru.veselov.restaurantvoting.repository.UserRepository;
import ru.veselov.restaurantvoting.security.AuthorizedUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: %s".formatted(email)));
        return new AuthorizedUser(user);
    }
}
