package ru.otus.spring.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.AuthorRepository;

import java.util.List;

@Component
@Slf4j
@SuppressWarnings("unused")
public class AuthorServiceImpl implements AuthorService {

    public static final String AUTHOR_SERVICE = "authorService";

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Bulkhead(name = AUTHOR_SERVICE, fallbackMethod = "bulkheadSave")
    @CircuitBreaker(name = AUTHOR_SERVICE, fallbackMethod = "bulkheadSave")
    @Transactional
    @Override
    public Author save(Author author) {
        return repository.save(author);
    }

    @CircuitBreaker(name = AUTHOR_SERVICE, fallbackMethod = "bulkheadGetById")
    @Bulkhead(name = AUTHOR_SERVICE, fallbackMethod = "bulkheadGetById")
    @Override
    public Author getById(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @CircuitBreaker(name = AUTHOR_SERVICE, fallbackMethod = "bulkheadGetAll")
    @Bulkhead(name = AUTHOR_SERVICE, fallbackMethod = "bulkheadGetAll")
    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }

    @CircuitBreaker(name = AUTHOR_SERVICE, fallbackMethod = "bulkheadDelete")
    @Bulkhead(name = AUTHOR_SERVICE, fallbackMethod = "bulkheadDelete")
    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    private List<Author> bulkheadGetAll(Exception e) {
        log.info("Bulkhead get all author");
        return List.of(new Author(0, "no name"));
    }

    private Author bulkheadGetById(long id, Exception e) {
        log.info("Bulkhead get author");
        return new Author(0, "no name");
    }

    private Author bulkheadSave(Author author, Exception e) {
        log.info("Bulkhead save author");
        return new Author(0, "no name");
    }

    private Author bulkheadDelete(long id, Exception e) {
        log.info("Bulkhead delete author");
        return new Author(0, "no name");
    }

}
