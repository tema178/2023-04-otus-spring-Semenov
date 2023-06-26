package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;
import java.util.Map;

@ConfigurationProperties(prefix = "application")
public class AppProps implements LocaleProvider, CsvPathProvider, CountOfAnswersForPassQuizProvider {

    public static final String DEFAULT_PATH_PROPERTY_KEY = "default";

    private final int countOfAnswersForPassQuiz;

    private final Locale locale;

    private final Map<String, String> csvPaths;

    @ConstructorBinding
    public AppProps(int countOfAnswersForPassQuiz, Locale locale, Map<String, String> csvPath) {
        this.countOfAnswersForPassQuiz = countOfAnswersForPassQuiz;
        this.locale = locale;
        this.csvPaths = csvPath;
    }

    @Override
    public int getCountOfAnswersForPassQuiz() {
        return countOfAnswersForPassQuiz;
    }


    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public String getCsvPath() {
        return csvPaths.getOrDefault(locale.getLanguage(), csvPaths.get(DEFAULT_PATH_PROPERTY_KEY));
    }
}
