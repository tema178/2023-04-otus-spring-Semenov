package ru.otus.example.springbatch.model.h2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {

    private long id;

    private String name;

    private AuthorDto author;

    private GenreDto genre;

    private List<CommentDto> comments;

}
