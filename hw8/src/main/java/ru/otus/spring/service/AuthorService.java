package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.DeletedAuthorHasBooksException;

import java.util.List;

public interface AuthorService {
    Author save(Author author);

    Author getById(String id);

    List<Author> getAll();

    void deleteById(String id) throws DeletedAuthorHasBooksException;
}
