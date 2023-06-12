package ru.otus.service;

import ru.otus.dao.QuizDao;
import ru.otus.exceptions.DaoException;
import ru.otus.utils.QuizPrinter;

public class QuizService {

    private final QuizDao quizDao;

    private final QuizPrinter quizPrinter;

    private final StreamPrintService streamPrintService;

    public QuizService(QuizDao quizCsvDao, QuizPrinter quizPrinter, StreamPrintService streamPrintService) {
        this.quizDao = quizCsvDao;
        this.quizPrinter = quizPrinter;
        this.streamPrintService = streamPrintService;
    }

    public void printQuiz() {
        try {
            quizPrinter.printQuiz(quizDao.getQuestions());
        } catch (DaoException e) {
            streamPrintService.printf("Application error: %s", e.getMessage());
        }
    }
}
