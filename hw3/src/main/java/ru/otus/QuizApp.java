package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class QuizApp {
    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("ru-RU"));
        SpringApplication.run(QuizApp.class, args);
    }
}
