package ru.otus.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.config.AppProps;
import ru.otus.domain.Answer;
import ru.otus.domain.Quiz;
import ru.otus.exceptions.DaoException;
import ru.otus.utils.CsvQuizReader;

import java.util.List;
import java.util.Locale;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CsvQuizDaoTest {

    @Mock
    private MessageSource messageSource;

    private final AppProps appProps = new AppProps(3, Locale.ENGLISH);

    @Test
    @DisplayName("Integration test of reading quiz from csv")
    void csvQuizDaoIntegrationTest() throws DaoException {
        given(messageSource.getMessage("csvPath", null, appProps.getLocale())).willReturn("quizTest.csv");
        CsvQuizDao csvQuizDao = new CsvQuizDao(new CsvQuizReader(messageSource, appProps));
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
