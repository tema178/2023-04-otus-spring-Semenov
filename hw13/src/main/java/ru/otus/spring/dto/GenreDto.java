package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.domain.Genre;

@Data
@AllArgsConstructor
public class GenreDto {

    private long id;

    private String name;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public Genre toDomainObject(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getName());
    }
}
