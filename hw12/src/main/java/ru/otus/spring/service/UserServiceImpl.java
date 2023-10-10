package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.repository.UserRepository;

import java.util.List;

@Component
public class UserServiceImpl extends JdbcUserDetailsManager implements UserService {

    private final UserRepository repository;


    public UserServiceImpl(UserRepository repository, JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate.getDataSource());
        this.repository = repository;
    }

    @Override
    public UserDto find(String name) {
        return UserDto.userToDto(repository.findById(name).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<UserDto> findAll() {
        return UserDto.userListToDtoList(repository.findAll());
    }
}
