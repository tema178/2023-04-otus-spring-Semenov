package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с комментариями должно")
@DataJpaTest
class CommentRepositoryJpaTest {

    @Autowired
    @SuppressWarnings("unused")
    private CommentRepository repository;

    @DisplayName("Получить комментарии для книги")
    @Test
    void shouldGetCommentById() {
        List<Comment> allCommentsForBook = repository.getCommentsByBookId(2);
        assertThat(allCommentsForBook).hasSize(2);
    }
}