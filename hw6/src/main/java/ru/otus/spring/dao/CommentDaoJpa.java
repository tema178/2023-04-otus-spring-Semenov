package ru.otus.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
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
        TypedQuery<Comment> query = em.createQuery("select s from Comment s where bookId = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public int update(long id, String comment) {
        Comment original = em.find(Comment.class, id);
        original.setBody(comment);
        em.merge(original);
        return 1;
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Comment where id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
