package ru.otus.spring.repository;

import ru.otus.spring.domain.Comment;

public interface BookWithCommentsRepositoryCustom {

    void addCommentToBook(String bookId, Comment comment);

    void updateCommentToBook(String bookId, Comment comment);

    void deleteCommentFromBook(String bookId, String commentId);


}
