package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookWithComments;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.exceptions.BookServiceException;

import java.util.List;

public interface BookService {
    Book create(String bookName, String authorId, String genreId) throws BookServiceException;

    void update(String id, String bookName, String authorId, String genreId) throws BookServiceException;

    List<Book> findAll();

    BookWithComments getById(String id);

    void deleteById(String id);

    void addComment(String bookId, Comment comment);

    void deleteComment(String bookId, String commentId);

    void updateComment(String bookId, Comment newComment);
}
