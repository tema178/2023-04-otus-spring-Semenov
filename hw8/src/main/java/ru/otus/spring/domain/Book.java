package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("books")
public class Book {

    @Id
    private String id;

    private String name;

    private Author author;

    private Genre genre;

    private List<Comment> comments;

    public Book(String name, Author author, Genre genre) {
        this.id = null;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = Collections.emptyList();
    }

    public Book(String id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = Collections.emptyList();
    }

    public Book(String name, Author author, Genre genre, List<Comment> comments) {
        this.id = null;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

}
