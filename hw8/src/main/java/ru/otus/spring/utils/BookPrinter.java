package ru.otus.spring.utils;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookWithoutComments;

import java.util.List;

public interface BookPrinter {
    void printWithComments(Book book);

    void print(Book book);

    void print(BookWithoutComments book);

    void print(String prefix, Book book);

    void print(List<Book> books);

    void printWithoutComments(List<BookWithoutComments> books);
}
