package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.domain.Comment;

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
    public Comment findById(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

}
