package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorService {
    Author create(Author author);

    Author getById(long id);

    List<Author> getAll();

    int update(Author author);

    Author deleteById(long id);
}
