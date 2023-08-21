package ru.otus.spring.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.BookWithComments;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookWithCommentsRepository;
import ru.otus.spring.repository.GenreRepository;

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
    public void insertBook(BookWithCommentsRepository bookRepository, AuthorRepository authorRepository,
                           GenreRepository genreRepository) {
        Author author = authorRepository.findById(ivanId.toString()).orElseThrow();
        Genre genre = genreRepository.findById(actionId.toString()).orElseThrow();
        BookWithComments book = new BookWithComments("Book of Ivan 1", author, genre,
                List.of(new Comment(ObjectId.get().toString(), "Comment 1")));
        bookRepository.save(book);
    }
}
