package ru.otus.comand_line_runners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.service.QuizService;

@Component
@SuppressWarnings("unused")
public class QuizAppRunner implements CommandLineRunner {

    private final QuizService quizService;

    public QuizAppRunner(QuizService quizService) {
        this.quizService = quizService;
    }

    @Override
    public void run(String... args) {
        quizService.startQuiz();
    }
}
