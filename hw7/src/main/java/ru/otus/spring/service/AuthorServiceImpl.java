package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.List;

@Component
@SuppressWarnings("unused")
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao dao;

    public AuthorServiceImpl(AuthorDao dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public Author create(Author author) {
        return dao.create(author);
    }

    @Override
    public Author getById(long id) {
        return dao.getById(id);
    }

    @Override
    public List<Author> getAll() {
        return dao.getAll();
    }

    @Transactional
    @Override
    public void update(Author author) {
        dao.update(author);
    }

    @Transactional
    @Override
    public Author deleteById(long id) {
        return dao.deleteById(id);
    }

}
