package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.events.GenreUpdateEventListener;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
@Import(GenreUpdateEventListener.class)
@SuppressWarnings("unused")
@DisplayName("Репозиторий жанров с listener-ами должен ")
class GenreRepositoryWithListenerTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Обновлять жанр в книгах после обновления названия жанра")
    void shouldUpdateGenreInfoInBooksAfterGenreUpdate() {
        Book book = bookRepository.findById("1").orElseThrow();
        assertThat(book.getGenre().getName()).isEqualTo("Action");
        genreRepository.save(new Genre("1", "Fantasy Action"));
        book = bookRepository.findById("1").orElseThrow();
        assertThat(book.getGenre().getName()).isEqualTo("Fantasy Action");
    }
}
