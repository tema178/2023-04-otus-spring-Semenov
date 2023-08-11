package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Comment;

import java.util.List;

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
        return dao.create(comment);
    }

    @Override
    public List<Comment> getAllCommentsForBook(long bookId) {
        return dao.getAllCommentsForBook(bookId);
    }

    @Transactional
    @Override
    public void update(long id, String comment) {
        dao.update(id, comment);
    }

    @Transactional
    @Override
    public Comment deleteById(long id) {
        return dao.deleteById(id);
    }

}
