package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.events.AuthorUpdateEventListener;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
@Import(AuthorUpdateEventListener.class)
@SuppressWarnings("unused")
@DisplayName("Репозиторий авторов с listener-ами должен ")
class AuthorRepositoryWithListenerTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Обновлять автора в книгах после обновления имени автора")
    void shouldUpdateAuthorInfoInBooksAfterAuthorUpdate() {
        Book book = bookRepository.findById("1").orElseThrow();
        assertThat(book.getAuthor().getName()).isEqualTo("Ivan");
        authorRepository.save(new Author("2", "Ivan Vasilievich"));
        book = bookRepository.findById("1").orElseThrow();
        assertThat(book.getAuthor().getName()).isEqualTo("Ivan Vasilievich");
    }
}
