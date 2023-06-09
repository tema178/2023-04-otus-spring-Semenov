package ru.otus.utils;

import ru.otus.domain.Quiz;
import ru.otus.service.PrintService;

import java.util.List;

public class QuizPrinter {

    private final PrintService printService;

    public QuizPrinter(PrintService printService) {
        this.printService = printService;
    }

    public void printQuiz(List<Quiz> quizList) {
        quizList.forEach(q -> {
            printService.println(q.getQuestion());
            List<Quiz.Answer> answers = q.getAnswers();
            for (int numberOfAnswer = 0; numberOfAnswer < answers.size(); numberOfAnswer++) {
                printService.printf("%s. %s%n", numberOfAnswer + 1, answers.get(numberOfAnswer).getValue());
            }
        });
    }
}
