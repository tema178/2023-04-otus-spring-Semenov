package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.domain.Comment;

import java.util.List;

import static ru.otus.spring.exceptions.ExceptionUtil.entityNotFoundExceptionMessageFormat;

@Component
@SuppressWarnings("unused")
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Comment create(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public List<Comment> getAllCommentsForBook(long bookId) {
        return repository.getCommentsByBookId(bookId);
    }

    @Transactional
    @Override
    public void update(long id, String comment) {
        Comment original = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(entityNotFoundExceptionMessageFormat("Author", id)));
        original.setBody(comment);
        repository.save(original);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

}
