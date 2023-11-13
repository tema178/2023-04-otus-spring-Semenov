package ru.otus.example.springbatch.service;

import lombok.Getter;
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

    private long authorIncrement = 0;
    private long genreIncrement = 0;
    private long bookIncrement = 0;
    private long commentIncrement = 0;

    public BookDto transform(BookWithComments book){
        bookIdMapping.put(book.getId(), ++bookIncrement);
        List<CommentDto> bookComments = commentToDto(book.getComments(), bookIncrement);
        comments.addAll(bookComments);
        return new BookDto(bookIncrement, book.getName(),
                new AuthorDto(authorIdMapping.get(book.getAuthor().getId()), book.getAuthor().getName()),
                new GenreDto(genreIdMapping.get(book.getGenre().getId()), book.getGenre().getName()),
                comments);
    }

    public CommentDto toDto(Comment comment, Long bookId) {
        return new CommentDto(++commentIncrement, comment.getText(), bookId);
    }

    public List<CommentDto> commentToDto(List<Comment> comment, Long bookId) {
        if (comment == null) {
            return Collections.emptyList();
        }
        return comment.stream().map(c -> toDto(c, bookId)).toList();
    }

    public AuthorDto transform(Author author) {
        authorIdMapping.put(author.getId(), ++authorIncrement);
        return new AuthorDto(authorIncrement, author.getName());
    }

    public GenreDto transform(Genre author) {
        genreIdMapping.put(author.getId(), ++genreIncrement);
        return new GenreDto(genreIncrement, author.getName());
    }
}
