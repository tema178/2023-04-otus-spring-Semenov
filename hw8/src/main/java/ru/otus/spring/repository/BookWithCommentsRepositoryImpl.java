package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.domain.BookWithComments;
import ru.otus.spring.domain.Comment;

@RequiredArgsConstructor
public class BookWithCommentsRepositoryImpl implements BookWithCommentsRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void addCommentToBook(String bookId, Comment comment) {
        val query = Query.query(Criteria.where("id").is(new ObjectId(bookId)));
        val update = new Update().push("comments", comment);
        mongoTemplate.updateFirst(query, update, BookWithComments.class);
    }

    @Override
    public void deleteCommentFromBook(String bookId, String commentId) {
        val query = Query.query(Criteria.where("id").is(new ObjectId(commentId)));
        val bookQuery = Query.query(Criteria.where("id").is(new ObjectId(bookId)));
        val update = new Update().pull("comments", query);
        mongoTemplate.updateFirst(bookQuery, update, BookWithComments.class);
    }

    @Override
    public void updateCommentToBook(String bookId, Comment comment) {
        val bookQuery = Query.query(Criteria.where("id")
                .is(new ObjectId(bookId)).and("comments.id").is(comment.getId()));
        val update = new Update().set("comments.$.text", comment.getText());
        mongoTemplate.updateFirst(bookQuery, update, BookWithComments.class);
    }

}
