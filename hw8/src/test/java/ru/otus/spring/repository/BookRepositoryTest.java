package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("Репозиторий для работы с книгами должен")
class BookRepositoryTest {

    @Autowired
    @SuppressWarnings("unused")
    private BookRepository bookRepository;

    @Test
    @DisplayName("Находить книгу по id автора")
    void shouldFindBooksByAuthorId() {
        List<Book> nikolay = bookRepository.findByAuthorId("1");
        assertThat(nikolay).hasSize(1);
        assertThat(nikolay.get(0).getName()).isEqualTo("Book of Nikolay 1");
    }

    @Test
    @DisplayName("Находить книгу по id жанра")
    void shouldFindBooksGenreId() {
        List<Book> nikolay = bookRepository.findByGenreId("1");
        assertThat(nikolay).hasSize(1);
        assertThat(nikolay.get(0).getName()).isEqualTo("Book of Ivan 1");
    }
}
