package ru.otus.spring.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@ChangeLog(order = "001")
@SuppressWarnings("unused")
public class DatabaseChangelog {

    private Author nikolay;

    private Author ivan;

    private Genre action;

    private Genre adventure;

    @ChangeSet(order = "001", id = "dropDb", author = "Tema", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "Tema", runAlways = true)
    public void insertAuthors(AuthorRepository authorRepository) {
        nikolay = new Author("1", "Nikolay");
        nikolay = authorRepository.save(nikolay);
        ivan = new Author("2", "Ivan");
        ivan = authorRepository.save(ivan);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "Tema", runAlways = true)
    public void insertGenres(GenreRepository genreRepository) {
        action = new Genre("1", "Action");
        action = genreRepository.save(action);
        adventure = new Genre("2", "Adventure");
        adventure = genreRepository.save(adventure);
    }

    @ChangeSet(order = "004", id = "insertBook", author = "Tema", runAlways = true)
    public void insertBook(BookRepository bookRepository) {
        Book book = new Book("1", "Book of Ivan 1", ivan, action,
                List.of(new Comment(ObjectId.get().toString(), "Comment 1")));
        bookRepository.save(book);
        Book book2 = new Book("2", "Book of Nikolay 1", nikolay, adventure,
                List.of(new Comment(ObjectId.get().toString(), "Comment 1")));
        bookRepository.save(book2);
    }
}
