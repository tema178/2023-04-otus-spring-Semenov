package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void updateAuthor(Author author) {
        val query = Query.query(Criteria.where("author.id").is(new ObjectId(author.getId())));
        val update = new Update().set("author", author);
        mongoTemplate.updateMulti(query, update, Book.class);
    }

    @Override
    public void updateGenre(Genre genre) {
        val query = Query.query(Criteria.where("genre.id").is(new ObjectId(genre.getId())));
        val update = new Update().set("genre", genre);
        mongoTemplate.updateMulti(query, update, Book.class);
    }
}
