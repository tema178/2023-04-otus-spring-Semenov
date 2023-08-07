package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.BookServiceException;

import java.util.List;

public interface BookService {
    Book create(String bookName, long authorId, long genreId) throws BookServiceException;

    boolean update(long id, String bookName, long authorId, long genreId) throws BookServiceException;

    List<Book> getAll();

    Book getById(long id);

    Book deleteById(long id);
}
