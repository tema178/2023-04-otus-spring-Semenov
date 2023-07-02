package ru.otus.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.domain.Answer;
import ru.otus.domain.Quiz;
import ru.otus.service.IOServiceStreams;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class QuizPrinterTest {

    @Mock
    private IOServiceStreams streamPrintService;

    @DisplayName("Test quiz print format")
    @Test
    void printQuizTest() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        Quiz quizData = new Quiz("Anemophobia is the fear of what?",
                List.of(new Answer("Dark", false),
                        new Answer("Fire", false),
                        new Answer("Wind", true)));

        doAnswer(invocationOnMock -> {
            ps.println(invocationOnMock.getArgument(0).toString());
            return null;
        }).when(streamPrintService).outputString(anyString());

        doAnswer(invocationOnMock -> {
            ps.printf(invocationOnMock.getArgument(0).toString(), invocationOnMock.getArgument(1),
                    invocationOnMock.getArgument(2));
            return null;
        }).when(streamPrintService).outputFormatString(anyString(), any(), any());

        new QuizPrinter(streamPrintService).printQuiz(quizData);

        String expected = """
                Anemophobia is the fear of what?
                1. Dark
                2. Fire
                3. Wind
                """.replaceAll("\n", System.lineSeparator());
        assertEquals(baos.toString(), expected);
    }
}
