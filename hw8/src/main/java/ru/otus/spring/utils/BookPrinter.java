package ru.otus.spring.utils;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookWithComments;

import java.util.List;

public interface BookPrinter {

    void print(Book book);

    void print(BookWithComments book);

    void print(String prefix, Book book);

    void print(List<Book> books);
}
