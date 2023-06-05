package ru.otus.utils;

import ru.otus.domain.QuizBody;

import java.util.List;

public class QuizPrinter {

    public void printQuiz(List<QuizBody> quizBodies) {
        quizBodies.forEach(q -> {
            System.out.println(q.getQuestion());
            List<String> answers = q.getAnswers();
            for (int numberOfAnswer = 0; numberOfAnswer < answers.size(); numberOfAnswer++) {
                System.out.printf("%s. %s%n", numberOfAnswer + 1, answers.get(numberOfAnswer));
            }
        });
    }
}
