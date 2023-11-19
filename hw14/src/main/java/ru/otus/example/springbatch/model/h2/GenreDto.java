package ru.otus.example.springbatch.model.h2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreDto {

    private long id;

    private String name;

    private String mongoId;

    public GenreDto(String name, String mongoId) {
        this.name = name;
        this.mongoId = mongoId;
    }
}
