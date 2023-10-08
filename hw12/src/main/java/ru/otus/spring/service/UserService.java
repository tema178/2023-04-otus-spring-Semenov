package ru.otus.spring.service;

import ru.otus.spring.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto find(String name);

    List<UserDto> findAll();
}
