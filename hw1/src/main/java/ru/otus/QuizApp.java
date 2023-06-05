package ru.otus;

import ru.otus.service.QuizService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class QuizApp {
    public static void main( String[] args ) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        context.getBean(QuizService.class).printQuiz();
        context.close();
    }
}
