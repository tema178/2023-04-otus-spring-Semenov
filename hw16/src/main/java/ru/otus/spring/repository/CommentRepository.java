package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.domain.Comment;

import java.util.List;


@RepositoryRestResource(path = "comment")
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @RestResource(path = "commentsByBookId", rel = "commentsByBookId")
    List<Comment> getCommentsByBookId(long bookId);
}
