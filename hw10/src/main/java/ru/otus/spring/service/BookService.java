package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.BookServiceException;

import java.util.List;

public interface BookService {
    Book save(Book book) throws BookServiceException;

    List<Book> getAll();

    Book getById(long id);

    void deleteById(long id);
}
