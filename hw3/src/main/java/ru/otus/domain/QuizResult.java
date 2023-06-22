package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuizResult {

    private List<Quiz> quizList;

    private Person respondent;

    private int countOfTrueAnswers;
}
