package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "authors")
public class Author {

    @Id
    private String id;

    private String name;

    public Author(String name) {
        this.id = null;
        this.name = name;
    }
}
