package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.events.GenreUpdateEventListener;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@Import(GenreUpdateEventListener.class)
@SuppressWarnings("unused")
@DisplayName("Репозиторий жанров с listener-ами должен ")
class GenreRepositoryWithListenerTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Обновлять жанр в книгах после обновления названия жанра")
    void shouldUpdateGenreInfoInBooksAfterGenreUpdate() {
        Book book = mongoTemplate.findById("64eb3b278b24173141bc2615", Book.class);
        assertNotNull(book);
        assertThat(book.getGenre().getName()).isEqualTo("Action");
        genreRepository.save(new Genre("64eb3b278b24173141bc2613", "Fantasy Action"));
        book = mongoTemplate.findById("64eb3b278b24173141bc2615", Book.class);
        assertNotNull(book);
        assertThat(book.getGenre().getName()).isEqualTo("Fantasy Action");
    }
}
