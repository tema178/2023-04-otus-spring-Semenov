package ru.otus.dao;

import ru.otus.domain.QuizBody;
import ru.otus.utils.QuizReader;

import java.io.IOException;
import java.util.List;

public class QuizDao {

    private final QuizReader reader;

    public QuizDao(QuizReader reader) {
        this.reader = reader;
    }

    public List<QuizBody> getQuestions() throws IOException {
        return reader.getAllQuestions();
    }
}
