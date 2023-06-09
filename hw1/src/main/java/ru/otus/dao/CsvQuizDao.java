package ru.otus.dao;

import ru.otus.domain.Quiz;
import ru.otus.exceptions.DaoException;
import ru.otus.utils.QuizReader;

import java.io.IOException;
import java.util.List;

public class CsvQuizDao implements QuizDao{

    private final QuizReader reader;

    public CsvQuizDao(QuizReader reader) {
        this.reader = reader;
    }

    public List<Quiz> getQuestions() throws DaoException {
        try {
            return reader.getAllQuestions();
        } catch (IOException e){
            throw new DaoException("Problem with file reading: " + e.getMessage(), e);
        }
    }
}
