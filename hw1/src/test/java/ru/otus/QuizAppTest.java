package ru.otus;

import junit.framework.TestCase;
import org.mockito.Mockito;
import ru.otus.dao.CsvQuizDao;
import ru.otus.domain.Quiz;
import ru.otus.exceptions.DaoException;
import ru.otus.service.PrintService;
import ru.otus.service.QuizService;
import ru.otus.utils.QuizPrinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

public class QuizAppTest extends TestCase {

    /**
     * Test output format
     */
    public void testApp() throws DaoException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintService printService = new PrintService(ps);

        CsvQuizDao csvQuizDao = Mockito.mock(CsvQuizDao.class);
        Mockito.when(csvQuizDao.getQuestions()).thenReturn(
                List.of(new Quiz("Anemophobia is the fear of what?",
                        List.of(new Quiz.Answer("Dark", false),
                                new Quiz.Answer("Fire", false),
                                new Quiz.Answer("Wind", true)))));

        new QuizService(csvQuizDao, new QuizPrinter(printService), printService).printQuiz();
        assertEquals(baos.toString(), """
                Anemophobia is the fear of what?
                1. Dark
                2. Fire
                3. Wind
                """);

    }
}
