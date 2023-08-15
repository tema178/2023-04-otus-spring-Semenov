package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с комментариями должно")
@DataJpaTest
class CommentDaoJpaTest {

    @Autowired
    @SuppressWarnings("unused")
    private CommentDao commentDao;

    @Autowired
    @SuppressWarnings("unused")
    private TestEntityManager em;

    @DisplayName("Получить комментарии для книги")
    @Test
    void shouldGetCommentById() {
        List<Comment> allCommentsForBook = commentDao.getAllCommentsForBook(2);
        assertThat(allCommentsForBook).hasSize(2);
    }
}