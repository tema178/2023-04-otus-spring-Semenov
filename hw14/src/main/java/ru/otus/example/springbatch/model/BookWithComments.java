package ru.otus.example.springbatch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Document("books")
@Getter
public class BookWithComments {

    @Id
    private String id;

    private String name;

    private Author author;

    private Genre genre;

    private List<Comment> comments;

    public BookWithComments(String name, Author author, Genre genre, List<Comment> comments) {
        this.id = null;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }
}
