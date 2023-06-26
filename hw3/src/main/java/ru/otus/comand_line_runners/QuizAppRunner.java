package ru.otus.comand_line_runners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.service.QuizServiceInterface;

@Component
@SuppressWarnings("unused")
public class QuizAppRunner implements CommandLineRunner {

    private final QuizServiceInterface quizService;

    public QuizAppRunner(QuizServiceInterface quizService) {
        this.quizService = quizService;
    }

    @Override
    public void run(String... args) {
        quizService.startQuiz();
    }
}
