package ru.otus.spring.dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.util.List;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    public BookDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Book create(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("books-authors-genres-entity-graph");
        TypedQuery<Book> query = em.createQuery(
                "select s from Book s", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public int update(Book book) {
        em.merge(book);
        return 1;
    }

    @Override
    public Book deleteById(long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
        return book;
    }

}
