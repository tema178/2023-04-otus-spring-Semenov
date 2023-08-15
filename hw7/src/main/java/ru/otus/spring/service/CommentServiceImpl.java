package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Comment;

import java.util.List;

import static ru.otus.spring.exceptions.ExceptionUtil.entityNotFoundExceptionMessageFormat;

@Component
@SuppressWarnings("unused")
public class CommentServiceImpl implements CommentService {

    private final CommentDao dao;

    public CommentServiceImpl(CommentDao dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public Comment create(Comment comment) {
        return dao.save(comment);
    }

    @Override
    public List<Comment> getAllCommentsForBook(long bookId) {
        return dao.getAllCommentsForBook(bookId);
    }

    @Transactional
    @Override
    public void update(long id, String comment) {
        Comment original = dao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(entityNotFoundExceptionMessageFormat("Author", id)));
        original.setBody(comment);
        dao.save(original);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }

}
