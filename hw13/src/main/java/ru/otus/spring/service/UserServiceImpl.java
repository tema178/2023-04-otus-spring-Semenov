package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserDto find(String name) {
        return UserDto.userToDto(repository.findById(name).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<UserDto> findAll() {
        return UserDto.userListToDtoList(repository.findAll());
    }
}
