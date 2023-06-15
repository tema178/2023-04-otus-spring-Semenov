package ru.otus.utils;

import ru.otus.domain.Quiz;
import ru.otus.exceptions.QuizReaderException;

import java.util.List;

public interface QuizReader {

    List<Quiz> getAllQuestions() throws QuizReaderException;
}
