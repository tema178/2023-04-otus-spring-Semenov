package ru.otus.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.util.List;

@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private final EntityManager em;

    public GenreDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Genre create(Genre genre) {
        em.persist(genre);
        return genre;
    }

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select s from Genre s", Genre.class);
        return query.getResultList();
    }

    @Override
    public int update(Genre genre) {
        em.merge(genre);
        return 1;
    }

    @Override
    public Genre deleteById(long id) {
        Genre genre = em.find(Genre.class, id);
        if (genre != null) {
            em.remove(genre);
        }
        return genre;
    }

}
