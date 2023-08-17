package ru.otus.spring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "books-authors-genres-entity-graph",
        attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(targetEntity = Author.class)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne(targetEntity = Genre.class)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, mappedBy = "book")
    private List<Comment> comments;

    public Book(String name, Author author, Genre genre) {
        this.id = 0;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = Collections.emptyList();
    }

    public Book(long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = Collections.emptyList();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
