package ru.otus.example.springbatch.service;

import lombok.Getter;
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

@Getter
@Service
public class TransformIdService {

    private final Map<String, Long> authorIdMapping = new ConcurrentHashMap<>();
    private final Map<String, Long> genreIdMapping = new ConcurrentHashMap<>();
    private final Map<String, Long> bookIdMapping = new ConcurrentHashMap<>();

    public BookDto transform(Book book) {
        return new BookDto(book.getName(),
                authorIdMapping.get(book.getAuthor().getId()),
                genreIdMapping.get(book.getGenre().getId()),
                book.getId());
    }

    public CommentDto transform(CommentMongo comment) {
        return new CommentDto(comment.getText(), bookIdMapping.get(comment.getBookId()));
    }

    public AuthorDto transform(Author author) {
        return new AuthorDto(author.getName(), author.getId());
    }

    public GenreDto transform(Genre author) {
        return new GenreDto(author.getName(), author.getId());
    }
}
