package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.DeletedGenreHasBooksException;

import java.util.List;

public interface GenreService {
    Genre save(Genre genre);

    Genre getById(String id);

    List<Genre> getAll();

    void deleteById(String id) throws DeletedGenreHasBooksException;
}
