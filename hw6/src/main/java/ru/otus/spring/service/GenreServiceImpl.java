package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.List;

@Component
@SuppressWarnings("unused")
public class GenreServiceImpl implements GenreService {

    private final GenreDao dao;

    public GenreServiceImpl(GenreDao dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public Genre create(Genre genre) {
        return dao.create(genre);
    }

    @Override
    public Genre getById(long id) {
        return dao.getById(id);
    }

    @Override
    public List<Genre> getAll() {
        return dao.getAll();
    }

    @Transactional
    @Override
    public void update(Genre genre) {
        dao.update(genre);
    }

    @Transactional
    @Override
    public Genre deleteById(long id) {
        return dao.deleteById(id);
    }

}
