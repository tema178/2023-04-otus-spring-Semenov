package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

public interface CommentService {
    Comment create(Comment comment);

    Comment findById(long id);

    void deleteById(long id);
}
