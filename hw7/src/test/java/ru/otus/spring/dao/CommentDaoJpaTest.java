package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Dao для работы с комментариями должно")
@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {

    private static final String COMMENT = "Комментарий 1";

    private static final int COMMENT_ID = 3;

    private static final String COMMENT_BODY_2 = "Comment for book of Nikolay 2";

    private static final String NEW_COMMENT = "new comment";


    @Autowired
    @SuppressWarnings("unused")
    private CommentDaoJpa commentDao;

    @Autowired
    @SuppressWarnings("unused")
    private TestEntityManager em;

    @DisplayName("Создать новый комментарий")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldInsertNewComment() {
        commentDao.create(new Comment(1, COMMENT));
        Comment comment = em.find(Comment.class, COMMENT_ID);
        assertThat(comment.getId()).isEqualTo(COMMENT_ID);
        assertThat(comment.getBody()).isEqualTo(COMMENT);
    }

    @DisplayName("Получить комментарии для книги")
    @Test
    void shouldGetCommentById() {
        List<Comment> allCommentsForBook = commentDao.getAllCommentsForBook(2);
        assertThat(allCommentsForBook).hasSize(2);
    }

    @DisplayName("Обновить комментарий по id")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdateCommentById() {
        Comment comment = em.find(Comment.class, 2);
        assertThat(comment.getBody()).isEqualTo(COMMENT_BODY_2);
        commentDao.update(2, NEW_COMMENT);
        comment = em.find(Comment.class, 2);
        assertThat(comment.getBody()).isEqualTo(NEW_COMMENT);
    }

    @DisplayName("Удалить комментарий по id")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteCommentById() {
        Comment comment = em.find(Comment.class, 2);
        assertThat(comment.getBody()).isEqualTo(COMMENT_BODY_2);
        commentDao.deleteById(2);
        comment = em.find(Comment.class, 2);
        assertNull(comment);
    }

}