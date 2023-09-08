package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {

    private long id;

    private String name;

    private Author author;

    private Genre genre;

    private List<CommentDto> comments;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getName(), book.getAuthor(), book.getGenre(),
                CommentDto.toDto(book.getComments()));
    }

    public Book toDomainObject() {
        return new Book(this.getId(), this.getName(), this.getAuthor(), this.getGenre(),
                CommentDto.toDomainObject(this.getComments()));
    }
}
