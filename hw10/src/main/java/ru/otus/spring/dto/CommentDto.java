package ru.otus.spring.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CommentDto {

    private long id;

    private String body;

    private long bookId;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getBody(), comment.getBook().getId());
    }

    public static List<CommentDto> toDto(List<Comment> comment) {
        if (comment == null) {
            return Collections.emptyList();
        }
        return comment.stream().map(CommentDto::toDto).toList();
    }

    public static Comment toDomainObject(CommentDto dto) {
        Book book = new Book();
        book.setId(dto.getBookId());
        return new Comment(dto.getId(), book, dto.getBody());
    }

    public static List<Comment> toDomainObject(List<CommentDto> dtoList) {
        if (dtoList == null) {
            return Collections.emptyList();
        }
        return dtoList.stream().map(CommentDto::toDomainObject).toList();
    }

}
