package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Genre;


public interface GenreDao extends JpaRepository<Genre, Long> {
}
