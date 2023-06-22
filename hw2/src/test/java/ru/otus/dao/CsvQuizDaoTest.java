package ru.otus.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.domain.Answer;
import ru.otus.domain.Quiz;
import ru.otus.exceptions.DaoException;
import ru.otus.utils.CsvQuizReader;

import java.util.List;

class CsvQuizDaoTest {

    @Test
    @DisplayName("Integration test of reading quiz from csv")
    void csvQuizDaoIntegrationTest() throws DaoException {
        CsvQuizDao csvQuizDao = new CsvQuizDao(new CsvQuizReader("quizTest.csv"));
        List<Quiz> quizActual = csvQuizDao.getQuestions();
        List<Quiz> quizExpected = List.of(
                new Quiz("The song 'An Englishman in New York' was about which man?",
                        List.of(new Answer("Quentin Crisp", true),
                                new Answer("Sting", false),
                                new Answer("John Lennon", false))),
                new Quiz("Anemophobia is the fear of what?",
                        List.of(new Answer("Dark", false),
                                new Answer("Fire", false),
                                new Answer("Wind", true))));
        Assertions.assertArrayEquals(quizExpected.toArray(),quizActual.toArray());
    }

}
