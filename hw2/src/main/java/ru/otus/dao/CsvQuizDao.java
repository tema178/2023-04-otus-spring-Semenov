package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Quiz;
import ru.otus.exceptions.DaoException;
import ru.otus.exceptions.QuizReaderException;
import ru.otus.utils.QuizReader;

import java.util.List;

@Repository
public class CsvQuizDao implements QuizDao {

    private final QuizReader reader;

    public CsvQuizDao(QuizReader reader) {
        this.reader = reader;
    }

    public List<Quiz> getQuestions() throws DaoException {
        try {
            return reader.getAllQuestions();
        } catch (QuizReaderException e) {
            throw new DaoException("Problem with file reading: " + e.getMessage(), e);
        }
    }
}
