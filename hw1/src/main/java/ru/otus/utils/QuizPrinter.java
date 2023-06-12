package ru.otus.utils;

import ru.otus.domain.Answer;
import ru.otus.domain.Quiz;
import ru.otus.service.StreamPrintService;

import java.util.List;

public class QuizPrinter {

    private final StreamPrintService streamPrintService;

    public QuizPrinter(StreamPrintService streamPrintService) {
        this.streamPrintService = streamPrintService;
    }

    public void printQuiz(List<Quiz> quizList) {
        quizList.forEach(q -> {
            streamPrintService.println(q.getQuestion());
            List<Answer> answers = q.getAnswers();
            for (int numberOfAnswer = 0; numberOfAnswer < answers.size(); numberOfAnswer++) {
                streamPrintService.printf("%s. %s%n", numberOfAnswer + 1, answers.get(numberOfAnswer).getValue());
            }
        });
    }
}
