package ru.otus.spring.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@Component
@Slf4j
@SuppressWarnings("unused")
public class GenreServiceImpl implements GenreService {

    public static final String GENRE_SERVICE = "genreService";

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @CircuitBreaker(name = GENRE_SERVICE, fallbackMethod = "bulkheadSave")
    @Bulkhead(name = GENRE_SERVICE, fallbackMethod = "bulkheadSave")
    @Transactional
    @Override
    public Genre save(Genre genre) {
        return repository.save(genre);
    }

    @CircuitBreaker(name = GENRE_SERVICE, fallbackMethod = "bulkheadGetById")
    @Bulkhead(name = GENRE_SERVICE, fallbackMethod = "bulkheadGetById")
    @Override
    public Genre getById(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @CircuitBreaker(name = GENRE_SERVICE, fallbackMethod = "bulkheadGetAll")
    @Bulkhead(name = GENRE_SERVICE, fallbackMethod = "bulkheadGetAll")
    @Override
    public List<Genre> getAll() {
        return repository.findAll();
    }

    @CircuitBreaker(name = GENRE_SERVICE, fallbackMethod = "bulkheadDelete")
    @Bulkhead(name = GENRE_SERVICE, fallbackMethod = "bulkheadDelete")
    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    private List<Genre> bulkheadGetAll(Exception e) {
        log.info("Bulkhead get all genres");
        return List.of(new Genre(0, "no name"));
    }

    private Genre bulkheadGetById(long id, Exception e) {
        log.info("Bulkhead get genres");
        return new Genre(0, "no name");
    }

    private Genre bulkheadSave(Genre genre, Exception e) {
        log.info("Bulkhead save genre");
        return new Genre(0, "no name");
    }

    private Genre bulkheadDelete(long id, Exception e) {
        log.info("Bulkhead delete genre");
        return new Genre(0, "no name");
    }

}
