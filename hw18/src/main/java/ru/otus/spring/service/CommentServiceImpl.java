package ru.otus.spring.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.CommentRepository;

@Component
@Slf4j
@SuppressWarnings("unused")
public class CommentServiceImpl implements CommentService {

    public static final String COMMENT_SERVICE = "commentService";

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @CircuitBreaker(name = COMMENT_SERVICE, fallbackMethod = "bulkheadSave")
    @Bulkhead(name = COMMENT_SERVICE, fallbackMethod = "bulkheadSave")
    @Transactional
    @Override
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @CircuitBreaker(name = COMMENT_SERVICE, fallbackMethod = "bulkheadGetById")
    @Bulkhead(name = COMMENT_SERVICE, fallbackMethod = "bulkheadGetById")
    @Override
    public Comment findById(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @CircuitBreaker(name = COMMENT_SERVICE, fallbackMethod = "bulkheadDelete")
    @Bulkhead(name = COMMENT_SERVICE, fallbackMethod = "bulkheadDelete")
    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    private Comment bulkheadGetById(long id, Exception e) {
        log.info("Bulkhead get comment");
        return new Comment(0, "try later");
    }

    private Comment bulkheadSave(Comment comment, Exception e) {
        log.info("Bulkhead save comment");
        return new Comment(0, "try later");
    }

    private Comment bulkheadDelete(long id, Exception e) {
        log.info("Bulkhead delete comment");
        return new Comment(0, "try later");
    }
}
