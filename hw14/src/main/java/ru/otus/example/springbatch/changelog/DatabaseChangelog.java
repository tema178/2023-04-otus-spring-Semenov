package ru.otus.example.springbatch.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import ru.otus.example.springbatch.model.Author;
import ru.otus.example.springbatch.model.BookWithComments;
import ru.otus.example.springbatch.model.Comment;
import ru.otus.example.springbatch.model.Genre;

import java.util.List;

@ChangeLog(order = "001")
@SuppressWarnings("unused")
public class DatabaseChangelog {

    private final ObjectId nikolayId = ObjectId.get();

    private final ObjectId ivanId = ObjectId.get();

    private final ObjectId actionId = ObjectId.get();

    private final ObjectId adventureId = ObjectId.get();

    @ChangeSet(order = "001", id = "dropDb", author = "Tema", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "Tema")
    public void insertAuthors(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("authors");

        var nikolay = new Document().append("name", "Nikolay").append("_id", nikolayId);
        var ivan = new Document().append("name", "Ivan").append("_id", ivanId);
        myCollection.insertMany(List.of(nikolay, ivan));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "Tema")
    public void insertGenres(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("genres");
        var action = new Document().append("name", "Action").append("_id", actionId);
        var adventure = new Document().append("name", "Adventure").append("_id", adventureId);
        var comedy = new Document().append("name", "Comedy");
        var crime = new Document().append("name", "Crime");
        var fantasy = new Document().append("name", "Fantasy");
        var horror = new Document().append("name", "Horror");
        myCollection.insertMany(List.of(action, adventure, comedy, crime, fantasy, horror));
    }

    @ChangeSet(order = "004", id = "insertBook", author = "Tema")
    public void insertBook(MongockTemplate template) {
        template.save(new BookWithComments("Book of Nikolay", new Author(nikolayId.toString(), "Nikolay"),
                new Genre(actionId.toString(), "Action"),
                List.of(new Comment(ObjectId.get().toString(), "Comment 1"),
                        new Comment(ObjectId.get().toString(), "Comment 2"),
                        new Comment(ObjectId.get().toString(), "Comment 3"))));
        template.save(new BookWithComments("Book of Ivan", new Author(ivanId.toString(), "Ivan"),
                new Genre(adventureId.toString(), "Adventure"),
                List.of(new Comment(ObjectId.get().toString(), "Comment 1"),
                        new Comment(ObjectId.get().toString(), "Comment 2"),
                        new Comment(ObjectId.get().toString(), "Comment 3"))));
    }
}
