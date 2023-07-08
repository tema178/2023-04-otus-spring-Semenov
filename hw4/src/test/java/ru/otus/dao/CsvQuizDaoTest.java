package ru.otus.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.config.AppProps;
import ru.otus.config.QuizFilePathProvider;
import ru.otus.domain.Answer;
import ru.otus.domain.Quiz;
import ru.otus.exceptions.DaoException;

import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
@SuppressWarnings("unused")
class CsvQuizDaoTest {

    @MockBean(AppProps.class)
    private QuizFilePathProvider quizFilePathProvider;

    @Autowired
    private CsvQuizDao csvQuizDao;

    @Test
    @DisplayName("Integration test of reading quiz from csv")
    void csvQuizDaoIntegrationTest() throws DaoException {
        given(quizFilePathProvider.getPath()).willReturn("quizTest.csv");
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
        Assertions.assertArrayEquals(quizExpected.toArray(), quizActual.toArray());
    }

}
