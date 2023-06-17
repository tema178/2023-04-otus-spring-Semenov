package ru.otus.utils;

import org.springframework.stereotype.Component;
import ru.otus.domain.Answer;
import ru.otus.domain.Quiz;
import ru.otus.service.OutputService;

import java.util.List;

@Component
public class QuizPrinter {

    private final OutputService printService;

    public QuizPrinter(OutputService printService) {
        this.printService = printService;
    }

    public void printQuiz(Quiz q) {
        printService.outputString(q.getQuestion());
        List<Answer> answers = q.getAnswers();
        for (int numberOfAnswer = 0; numberOfAnswer < answers.size(); numberOfAnswer++) {
            printService.outputFormatString("%s. %s%n", numberOfAnswer + 1, answers.get(numberOfAnswer).getValue());
        }
    }
}
