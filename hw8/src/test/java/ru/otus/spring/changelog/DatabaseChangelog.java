package ru.otus.spring.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
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
        nikolay = new Author("64eb3b278b24173141bc2611", "Nikolay");
        nikolay = authorRepository.save(nikolay);
        ivan = new Author("64eb3b278b24173141bc2612", "Ivan");
        ivan = authorRepository.save(ivan);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "Tema", runAlways = true)
    public void insertGenres(GenreRepository genreRepository) {
        action = new Genre("64eb3b278b24173141bc2613", "Action");
        action = genreRepository.save(action);
        adventure = new Genre("64eb3b278b24173141bc2614", "Adventure");
        adventure = genreRepository.save(adventure);
    }

    @ChangeSet(order = "004", id = "insertBook", author = "Tema", runAlways = true)
    public void insertBook(BookWithCommentsRepository bookRepository) {
        BookWithComments book = new BookWithComments("64eb3b278b24173141bc2615", "Book of Ivan 1", ivan, action,
                List.of(new Comment(ObjectId.get().toString(), "Comment 1")));
        bookRepository.save(book);
        BookWithComments book2 = new BookWithComments("64eb3b278b24173141bc2616", "Book of Nikolay 1",
                nikolay, adventure, List.of(new Comment(ObjectId.get().toString(), "Comment 1")));
        bookRepository.save(book2);
        BookWithComments book3 = new BookWithComments("64eb3b278b24173141bc2617", "Book of Nikolay 2",
                nikolay, adventure, List.of(new Comment(ObjectId.get().toString(), "Comment 1")));
        bookRepository.save(book3);
    }
}
