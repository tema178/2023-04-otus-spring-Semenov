package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppProps {

    private final int countOfAnswersForPassQuiz;
    private final Locale locale;

    @ConstructorBinding
    public AppProps(int countOfAnswersForPassQuiz, Locale locale) {
        this.countOfAnswersForPassQuiz = countOfAnswersForPassQuiz;
        this.locale = locale;
    }

    public int getCountOfAnswersForPassQuiz() {
        return countOfAnswersForPassQuiz;
    }


    public Locale getLocale() {
        return locale;
    }
}
