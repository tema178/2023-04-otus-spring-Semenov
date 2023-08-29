package ru.otus.spring.repository;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

public interface BookRepositoryCustom {

    void updateAuthor(Author author);

    void updateGenre(Genre author);
}
