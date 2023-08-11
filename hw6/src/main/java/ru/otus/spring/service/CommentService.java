package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment create(Comment comment);

    List<Comment> getAllCommentsForBook(long bookId);

    void update(long id, String comment);

    Comment deleteById(long id);
}
