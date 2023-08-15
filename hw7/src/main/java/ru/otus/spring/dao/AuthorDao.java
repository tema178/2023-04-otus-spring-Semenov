package ru.otus.spring.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Author;

public interface AuthorDao extends JpaRepository<Author, Long> {
}
