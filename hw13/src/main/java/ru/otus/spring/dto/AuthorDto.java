package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.domain.Author;

@Data
@AllArgsConstructor
public class AuthorDto {

    private long id;

    private String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }

    public Author toDomainObject(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getName());
    }
}
