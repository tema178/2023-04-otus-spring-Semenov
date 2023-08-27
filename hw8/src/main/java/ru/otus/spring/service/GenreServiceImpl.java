package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.exceptions.DeletedGenreHasBooksException;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.domain.Genre;

import java.util.List;


@Component
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    private final BookRepository bookRepository;

    @Override
    public Genre save(Genre genre) {
        return repository.save(genre);
    }

    @Override
    public Genre getById(String id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Genre> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) throws DeletedGenreHasBooksException {
        if (bookRepository.existsByGenreId(id)) {
            throw new DeletedGenreHasBooksException();
        }
        repository.deleteById(id);
    }

}
