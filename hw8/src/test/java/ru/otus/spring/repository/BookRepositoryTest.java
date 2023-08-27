package ru.otus.spring.repository;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("Репозиторий для работы с книгами должен")
class BookRepositoryTest {

    public static final String NIKOLAY_I = "Nikolay I";

    public static final String FANTASY_ADVENTURE = "Fantasy adventure";

    @Autowired
    @SuppressWarnings("unused")
    private BookRepository bookRepository;

    @Autowired
    @SuppressWarnings("unused")
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Обновлять автора во всех книгах с данным автором")
    void shouldUpdateAuthorInAllBooks() {
        bookRepository.updateAuthor(new Author("64eb3b278b24173141bc2611", NIKOLAY_I));
        Book book2 = mongoTemplate.findById(new ObjectId("64eb3b278b24173141bc2616"), Book.class);
        Book book3 = mongoTemplate.findById(new ObjectId("64eb3b278b24173141bc2617"), Book.class);
        assertThat(book2.getAuthor().getName()).isEqualTo(NIKOLAY_I);
        assertThat(book3.getAuthor().getName()).isEqualTo(NIKOLAY_I);
    }

    @Test
    @DisplayName("Обновлять жанр во всех книгах с данным жанром")
    void shouldUpdateGenreInAllBooks() {
        bookRepository.updateGenre(new Genre("64eb3b278b24173141bc2614", FANTASY_ADVENTURE));
        Book book2 = mongoTemplate.findById(new ObjectId("64eb3b278b24173141bc2616"), Book.class);
        Book book3 = mongoTemplate.findById(new ObjectId("64eb3b278b24173141bc2617"), Book.class);
        assertThat(book2.getGenre().getName()).isEqualTo(FANTASY_ADVENTURE);
        assertThat(book3.getGenre().getName()).isEqualTo(FANTASY_ADVENTURE);
    }
}
