package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.List;

import static ru.otus.spring.exceptions.ExceptionUtil.entityNotFoundExceptionMessageFormat;

@Component
@SuppressWarnings("unused")
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao dao;

    public AuthorServiceImpl(AuthorDao dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public Author save(Author author) {
        return dao.save(author);
    }

    @Override
    public Author getById(long id) {
        return dao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(entityNotFoundExceptionMessageFormat("Author", id)));
    }

    @Override
    public List<Author> getAll() {
        return dao.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }

}
