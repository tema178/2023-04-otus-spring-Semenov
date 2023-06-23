package ru.otus.comand_line_runners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.service.QuizService;

@Component
@SuppressWarnings("unused")
public class PreparationDev implements CommandLineRunner {

    private final QuizService quizService;

    public PreparationDev(QuizService quizService) {
        this.quizService = quizService;
    }

    @Override
    public void run(String... args) {
        quizService.startQuiz();
    }
}
