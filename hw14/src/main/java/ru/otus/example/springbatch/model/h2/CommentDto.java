package ru.otus.example.springbatch.model.h2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {

    private long id;

    private String body;

    private long bookId;

}
