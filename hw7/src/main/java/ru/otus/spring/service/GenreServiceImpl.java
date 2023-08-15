package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static ru.otus.spring.exceptions.ExceptionUtil.entityNotFoundExceptionMessageFormat;

@Component
@SuppressWarnings("unused")
public class GenreServiceImpl implements GenreService {

    private final GenreDao dao;

    public GenreServiceImpl(GenreDao dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public Genre save(Genre genre) {
        return dao.save(genre);
    }

    @Override
    public Genre getById(long id) {
        Optional<Genre> optionalGenre = dao.findById(id);
        if (optionalGenre.isEmpty()) {
            throw new EntityNotFoundException(entityNotFoundExceptionMessageFormat("Genre", id));
        }
        return optionalGenre.get();
    }

    @Override
    public List<Genre> getAll() {
        return dao.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }

}
