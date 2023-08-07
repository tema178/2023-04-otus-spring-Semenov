package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Dao для работы с авторами должно")
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {

    private static final int EXPECTED_UPDATE_COUNT = 1;

    private static final int EXPECTED_AUTHORS_TABLE_SIZE = 3;

    private static final String PETR_NAME = "Petr";

    private static final int PETR_ID = 4;

    private static final String IVAN_NAME = "Ivan";

    private static final int IVAN_ID = 1;

    private static final String IVAN_NAME_NEW = "Ivan_New";

    private static final int ANNA_ID = 3;

    @Autowired
    @SuppressWarnings("unused")
    private AuthorDaoJpa authorDao;

    @Autowired
    @SuppressWarnings("unused")
    private TestEntityManager em;

    @DisplayName("Создать нового автора")
    @Test
    void shouldInsertNewAuthor() {
        authorDao.create(new Author(0, PETR_NAME));
        Author author = em.find(Author.class, PETR_ID);
        assertThat(author.getId()).isEqualTo(PETR_ID);
        assertThat(author.getName()).isEqualTo(PETR_NAME);
    }

    @DisplayName("Получить автора по id")
    @Test
    void shouldGetAuthorById() {
        Author author = authorDao.getById(IVAN_ID);
        assertThat(author.getId()).isEqualTo(IVAN_ID);
        assertThat(author.getName()).isEqualTo(IVAN_NAME);
    }

    @DisplayName("Получить всех авторов")
    @Test
    void shouldGetBooks() {
        List<Author> author = authorDao.getAll();
        assertThat(author).hasSize(EXPECTED_AUTHORS_TABLE_SIZE);
    }

    @DisplayName("Обновить автора по id")
    @Test
    void shouldUpdateAuthorById() {
        int updated = authorDao.update(new Author(IVAN_ID, IVAN_NAME_NEW));
        assertThat(updated).isEqualTo(EXPECTED_UPDATE_COUNT);
        Author author = em.find(Author.class, IVAN_ID);
        assertThat(author.getId()).isEqualTo(IVAN_ID);
        assertThat(author.getName()).isEqualTo(IVAN_NAME_NEW);
    }

    @DisplayName("Удалить автора по id")
    @Test
    void shouldDeleteAuthorById() {
        Author author = authorDao.deleteById(ANNA_ID);
        assertThat(author.getId()).isEqualTo(ANNA_ID);
        assertNull(em.find(Author.class, ANNA_ID));
    }

}