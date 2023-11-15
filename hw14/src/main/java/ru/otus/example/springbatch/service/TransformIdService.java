package ru.otus.example.springbatch.service;

import lombok.Getter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.otus.example.springbatch.model.Author;
import ru.otus.example.springbatch.model.BookWithComments;
import ru.otus.example.springbatch.model.Comment;
import ru.otus.example.springbatch.model.Genre;
import ru.otus.example.springbatch.model.h2.AuthorDto;
import ru.otus.example.springbatch.model.h2.BookDto;
import ru.otus.example.springbatch.model.h2.CommentDto;
import ru.otus.example.springbatch.model.h2.GenreDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransformIdService {

    private final Map<String, Long> authorIdMapping = new HashMap<>();
    private final Map<String, Long> genreIdMapping = new HashMap<>();
    private final Map<String, Long> bookIdMapping = new HashMap<>();

    @Getter
    private final List<CommentDto> comments = new ArrayList<>();


    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public TransformIdService(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    public BookDto transform(BookWithComments book) {
        var id = getSequence("BOOK_SEQUENCE");
        bookIdMapping.put(book.getId(), id);
        List<CommentDto> bookComments = commentToDto(book.getComments(), id);
        comments.addAll(bookComments);
        return new BookDto(id, book.getName(),
                new AuthorDto(authorIdMapping.get(book.getAuthor().getId()), book.getAuthor().getName()),
                new GenreDto(genreIdMapping.get(book.getGenre().getId()), book.getGenre().getName()),
                comments);
    }

    public CommentDto toDto(Comment comment, Long bookId) {
        var id = getSequence("COMMENT_SEQUENCE");
        return new CommentDto(id, comment.getText(), bookId);
    }

    public List<CommentDto> commentToDto(List<Comment> comment, Long bookId) {
        if (comment == null) {
            return Collections.emptyList();
        }
        return comment.stream().map(c -> toDto(c, bookId)).toList();
    }

    public AuthorDto transform(Author author) {
        var id = getSequence("AUTHOR_SEQUENCE");
        authorIdMapping.put(author.getId(), id);
        return new AuthorDto(id, author.getName());
    }

    public GenreDto transform(Genre author) {
        var id = getSequence("GENRE_SEQUENCE");
        genreIdMapping.put(author.getId(), id);
        return new GenreDto(id, author.getName());
    }

    private Long getSequence(String sequenceName) {
        var jdbc = namedParameterJdbcOperations.getJdbcOperations();
        return jdbc.queryForObject(String.format("SELECT NEXT VALUE FOR %s", sequenceName), Long.class);
    }
}
