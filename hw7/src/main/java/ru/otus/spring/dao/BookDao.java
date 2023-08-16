package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.comments where b.id = :id ")
    Optional<Book> getByIdWithInitializedComments(@Param("id") Long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    @Override
    List<Book> findAll();
}
