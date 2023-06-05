package ru.otus.utils;

import ru.otus.domain.QuizBody;

import java.io.IOException;
import java.util.List;

public interface QuizReader {
    String ARRAY_DELIMITER = ";";

    List<QuizBody> getAllQuestions() throws IOException;
}
