package ru.otus.dao;

import ru.otus.domain.Quiz;
import ru.otus.exceptions.DaoException;

import java.util.List;

public interface QuizDao {

    List<Quiz> getQuestions() throws DaoException;

}
