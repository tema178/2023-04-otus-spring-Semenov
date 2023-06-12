package ru.otus.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.dao.CsvQuizDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Quiz;
import ru.otus.exceptions.DaoException;
import ru.otus.service.StreamPrintService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuizPrinterTest {

    @Mock
    private CsvQuizDao csvQuizDao;

    @DisplayName("Test quiz print format")
    @Test
    void printQuizTest() throws DaoException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        StreamPrintService streamPrintService = new StreamPrintService(ps);

        List<Quiz> quizData = List.of(new Quiz("Anemophobia is the fear of what?",
                List.of(new Answer("Dark", false),
                        new Answer("Fire", false),
                        new Answer("Wind", true))));

        given(csvQuizDao.getQuestions()).willReturn(quizData);

        new QuizPrinter(streamPrintService).printQuiz(csvQuizDao.getQuestions());

        assertEquals(baos.toString(), """
                Anemophobia is the fear of what?
                1. Dark
                2. Fire
                3. Wind
                """);

    }
}
