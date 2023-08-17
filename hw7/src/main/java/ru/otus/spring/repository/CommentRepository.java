package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.domain.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select e from Comment e where e.book.id = :book_id")
    List<Comment> getAllCommentsForBook(@Param("book_id") long bookId);
}
