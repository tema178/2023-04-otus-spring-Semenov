package ru.otus.example.springbatch.service;

import org.springframework.stereotype.Service;
import ru.otus.example.springbatch.model.Author;
import ru.otus.example.springbatch.model.Book;
import ru.otus.example.springbatch.model.CommentMongo;
import ru.otus.example.springbatch.model.Genre;
import ru.otus.example.springbatch.model.h2.AuthorDto;
import ru.otus.example.springbatch.model.h2.BookDto;
import ru.otus.example.springbatch.model.h2.CommentDto;
import ru.otus.example.springbatch.model.h2.GenreDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TransformIdService {

    private static final Map<String, Long> authorIdMapping = new ConcurrentHashMap<>();
    private static final Map<String, Long> genreIdMapping = new ConcurrentHashMap<>();
    private static final Map<String, Long> bookIdMapping = new ConcurrentHashMap<>();

    private static final AtomicLong authorIncrement = new AtomicLong(100);
    private static final AtomicLong genreIncrement = new AtomicLong(100);
    private static final AtomicLong bookIncrement = new AtomicLong(100);
    private static final AtomicLong commentIncrement = new AtomicLong(100);

    public BookDto transform(Book book) {
        long id = bookIncrement.getAndAdd(1);
        bookIdMapping.put(book.getId(), id);
        return new BookDto(id, book.getName(),
                new AuthorDto(authorIdMapping.get(book.getAuthor().getId()), book.getAuthor().getName()),
                new GenreDto(genreIdMapping.get(book.getGenre().getId()), book.getGenre().getName()));
    }

    public CommentDto transform(CommentMongo comment) {
        long id = commentIncrement.getAndAdd(1);
        return new CommentDto(id, comment.getText(), bookIdMapping.get(comment.getBookId()));
    }

    public AuthorDto transform(Author author) {
        long id = authorIncrement.getAndAdd(1);
        authorIdMapping.put(author.getId(), id);
        return new AuthorDto(id, author.getName());
    }

    public GenreDto transform(Genre author) {
        long id = genreIncrement.getAndAdd(1);
        genreIdMapping.put(author.getId(), id);
        return new GenreDto(id, author.getName());
    }
}
