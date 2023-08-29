package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.events.AuthorUpdateEventListener;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@Import(AuthorUpdateEventListener.class)
@SuppressWarnings("unused")
@DisplayName("Репозиторий авторов с listener-ами должен ")
class AuthorRepositoryWithListenerTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Обновлять автора в книгах после обновления имени автора")
    void shouldUpdateAuthorInfoInBooksAfterAuthorUpdate() {
        Book book = mongoTemplate.findById("64eb3b278b24173141bc2615", Book.class);
        assertNotNull(book);
        assertThat(book.getAuthor().getName()).isEqualTo("Ivan");
        authorRepository.save(new Author("64eb3b278b24173141bc2612", "Ivan Vasilievich"));
        book = mongoTemplate.findById("64eb3b278b24173141bc2615", Book.class);
        assertNotNull(book);
        assertThat(book.getAuthor().getName()).isEqualTo("Ivan Vasilievich");
    }
}
