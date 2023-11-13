package ru.otus.example.springbatch.model.h2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDto {

    private long id;

    private String name;
}
