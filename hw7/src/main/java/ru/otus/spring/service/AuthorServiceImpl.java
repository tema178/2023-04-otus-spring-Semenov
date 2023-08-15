package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

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
        Optional<Author> optionalAuthor = dao.findById(id);
        if (optionalAuthor.isEmpty()) {
            throw new EntityNotFoundException(entityNotFoundExceptionMessageFormat("Author", id));
        }
        return optionalAuthor.get();
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
