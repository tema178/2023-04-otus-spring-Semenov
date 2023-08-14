package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {
    Book create(Book book);

    Book getById(long id);

    List<Book> getAll();

    void update(Book book);

    Book deleteById(long id);
}
