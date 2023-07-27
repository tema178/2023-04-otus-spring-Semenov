package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Comment;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dao для работы с комментариями должно")
@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {

    private static final int EXPECTED_DELETE_COUNT = 1;

    private static final String COMMENT = "Комментарий 1";

    private static final int COMMENT_ID = 3;


    private static final List<Comment> COMMENTS = new ArrayList<>() {
        {
            add(new Comment(1, 2, "Comment for book of Nikolay 1"));
            add(new Comment(2, 2, "Comment for book of Nikolay 2"));
        }
    };

    private static final String NEW_COMMENT = "new comment";


    @Autowired
    @SuppressWarnings("unused")
    private CommentDaoJpa commentDao;

    @DisplayName("Создать новый комментарий")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldInsertNewComment() {
        commentDao.create(new Comment(1, COMMENT));
        List<Comment> allCommentsForBook = commentDao.getAllCommentsForBook(1);
        assertThat(allCommentsForBook).hasSize(1);
        assertThat(allCommentsForBook.get(0).getId()).isEqualTo(COMMENT_ID);
        assertThat(allCommentsForBook.get(0).getBody()).isEqualTo(COMMENT);
    }

    @DisplayName("Получить комментарии для книги")
    @Test
    void shouldGetCommentById() {
        List<Comment> allCommentsForBook = commentDao.getAllCommentsForBook(2);
        assertThat(allCommentsForBook).hasSize(2);
        assertEquals(COMMENTS, allCommentsForBook);
    }

    @DisplayName("Обновить комментарий по id")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdateCommentById() {
        commentDao.create(new Comment(1, COMMENT));
        List<Comment> allCommentsForBook = commentDao.getAllCommentsForBook(1);
        assertThat(allCommentsForBook).hasSize(1);
        assertThat(allCommentsForBook.get(0).getId()).isEqualTo(COMMENT_ID);
        assertThat(allCommentsForBook.get(0).getBody()).isEqualTo(COMMENT);
        commentDao.update(3, NEW_COMMENT);
        allCommentsForBook = commentDao.getAllCommentsForBook(1);
        assertThat(allCommentsForBook).hasSize(1);
        assertThat(allCommentsForBook.get(0).getId()).isEqualTo(COMMENT_ID);
        assertThat(allCommentsForBook.get(0).getBody()).isEqualTo(NEW_COMMENT);
    }

    @DisplayName("Удалить комментарий по id")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteCommentById() {
        commentDao.create(new Comment(1, COMMENT));
        List<Comment> allCommentsForBook = commentDao.getAllCommentsForBook(1);
        assertThat(allCommentsForBook).hasSize(1);
        assertThat(allCommentsForBook.get(0).getId()).isEqualTo(COMMENT_ID);
        assertThat(allCommentsForBook.get(0).getBody()).isEqualTo(COMMENT);
        commentDao.update(3, NEW_COMMENT);
        int updated = commentDao.deleteById(3);
        assertThat(updated).isEqualTo(EXPECTED_DELETE_COUNT);
        assertThat(commentDao.getAllCommentsForBook(1)).isEmpty();
    }

}