package ru.otus.spring.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.User;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    public static final String USER_SERVICE = "userService";

    private final UserRepository repository;

    @CircuitBreaker(name = USER_SERVICE, fallbackMethod = "bulkheadGetByName")
    @Bulkhead(name = USER_SERVICE, fallbackMethod = "bulkheadGetByName")
    @Override
    public UserDto find(String name) {
        return UserDto.userToDto(repository.findById(name).orElseThrow(EntityNotFoundException::new));
    }

    @CircuitBreaker(name = USER_SERVICE, fallbackMethod = "bulkheadGetAll")
    @Bulkhead(name = USER_SERVICE, fallbackMethod = "bulkheadGetAll")
    @Override
    public List<UserDto> findAll() {
        return UserDto.userListToDtoList(repository.findAll());
    }

    @CircuitBreaker(name = USER_SERVICE, fallbackMethod = "bulkheadGetUserDetails")
    @Bulkhead(name = USER_SERVICE, fallbackMethod = "bulkheadGetUserDetails")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findById(username).orElseThrow(EntityNotFoundException::new);
    }

    private UserDto bulkheadGetByName(String user, Exception e) {
        log.info("Bulkhead get user");
        return new UserDto(user, false);
    }

    private List<UserDto> bulkheadGetAll(Exception e) {
        log.info("Bulkhead get all users");
        return List.of(new UserDto("user", false));
    }

    private UserDetails bulkheadGetUserDetails(String user, Exception e) {
        log.info("Bulkhead get userDetails");
        return new User();
    }
}
