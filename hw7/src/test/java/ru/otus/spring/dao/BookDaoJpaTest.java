package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Dao для работы с книгами должно")
@DataJpaTest
class BookDaoJpaTest {
    private static final long SECOND_BOOK_ID = 2;

    private static final String SECOND_BOOK_NAME = "Book of Nikolay";

    @Autowired
    @SuppressWarnings("unused")
    private BookDao bookDao;

    @Autowired
    @SuppressWarnings("unused")
    private TestEntityManager em;

    @DisplayName("Получить книгу по id с комментариями")
    @Test
    void shouldGetBookById() {
        Optional<Book> bookOptional = bookDao.getByIdWithInitializedComments(SECOND_BOOK_ID);
        assertTrue(bookOptional.isPresent());
        Book book = bookOptional.get();
        assertThat(book.getName()).isEqualTo(SECOND_BOOK_NAME);
        List<Comment> comments = book.getComments();
        assertThat(comments).hasSize(2);

    }


}