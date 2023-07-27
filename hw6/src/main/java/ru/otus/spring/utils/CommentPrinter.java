package ru.otus.spring.utils;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentPrinter {
    void print(Comment comment);

    void print(String prefix, Comment comment);

    void print(List<Comment> comments);
}
