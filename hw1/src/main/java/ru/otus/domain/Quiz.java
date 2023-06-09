package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Quiz {

    private String question;

    private List<Answer> answers;

    @Data
    @AllArgsConstructor
    public static class Answer {

        private String value;

        private boolean correct;
    }
}
