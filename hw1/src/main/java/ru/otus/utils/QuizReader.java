package ru.otus.utils;

import ru.otus.domain.Quiz;

import java.io.IOException;
import java.util.List;

public interface QuizReader {

    List<Quiz> getAllQuestions() throws IOException;
}
