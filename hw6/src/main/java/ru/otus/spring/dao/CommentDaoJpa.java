package ru.otus.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;

@Repository
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private final EntityManager em;

    public CommentDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment create(Comment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public List<Comment> getAllCommentsForBook(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select s from Comment s where book = :book", Comment.class);
        Book book = em.find(Book.class, bookId);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public void update(long id, String comment) {
        Comment original = em.find(Comment.class, id);
        original.setBody(comment);
        em.merge(original);
    }

    @Override
    public Comment deleteById(long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        }
        return comment;
    }
}
