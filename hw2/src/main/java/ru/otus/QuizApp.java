package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.QuizService;

@Configuration
@ComponentScan
public class QuizApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(QuizApp.class);
        context.getBean(QuizService.class).startQuiz();
        context.close();
    }
}
