package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Dao для работы с жанрами должно")
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    private static final int EXPECTED_UPDATE_COUNT = 1;

    private static final int EXPECTED_GENRES_TABLE_SIZE = 6;

    private static final String HISTORICAL_GENRE = "Historical";

    private static final String ACTION_GENRE = "Action";

    private static final long ACTION_ID = 1;

    private static final long HORROR_ID = 6;

    private static final String ACTION_GENRE_NEW = "Action_New";

    private static final int HISTORICAL_ID = 7;

    @Autowired
    @SuppressWarnings("unused")
    private GenreDao genreDao;

    @Autowired
    @SuppressWarnings("unused")
    private TestEntityManager em;

    @DisplayName("Создать новый жанр")
    @Test
    void shouldInsertNewGenre() {
        genreDao.create(new Genre(0, HISTORICAL_GENRE));
        Genre genre = em.find(Genre.class, HISTORICAL_ID);
        assertThat(genre.getName()).isEqualTo(HISTORICAL_GENRE);
    }

    @DisplayName("Получить жанр по id")
    @Test
    void shouldGetGenreById() {
        Genre genre = genreDao.getById(ACTION_ID);
        assertThat(genre.getName()).isEqualTo(ACTION_GENRE);
    }

    @DisplayName("Получить все жанры")
    @Test
    void shouldGetBooks() {
        List<Genre> genre = genreDao.getAll();
        assertThat(genre).hasSize(EXPECTED_GENRES_TABLE_SIZE);
    }

    @DisplayName("Обновить жанр по id")
    @Test
    void shouldUpdateGenreById() {
        int updated = genreDao.update(new Genre(ACTION_ID, ACTION_GENRE_NEW));
        assertThat(updated).isEqualTo(EXPECTED_UPDATE_COUNT);
        Genre genre = em.find(Genre.class, ACTION_ID);
        assertThat(genre.getName()).isEqualTo(ACTION_GENRE_NEW);
    }

    @DisplayName("Удалить жанр по id")
    @Test
    void shouldDeleteGenreById() {
        Genre genre = genreDao.deleteById(HORROR_ID);
        assertThat(genre.getId()).isEqualTo(HORROR_ID);
        assertNull(em.find(Genre.class, HORROR_ID));

    }

}