package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author create(Author author);

    Author getById(long id);

    List<Author> getAll();

    void update(Author author);

    Author deleteById(long id);
}
