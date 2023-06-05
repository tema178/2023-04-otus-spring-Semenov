package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class QuizBody {

    private String question;

    private List<String> answers;

    private int numberOfTrueAnswer;
}
