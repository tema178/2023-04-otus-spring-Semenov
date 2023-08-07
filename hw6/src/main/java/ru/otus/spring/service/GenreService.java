package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreService {
    Genre create(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    int update(Genre genre);

    Genre deleteById(long id);
}
