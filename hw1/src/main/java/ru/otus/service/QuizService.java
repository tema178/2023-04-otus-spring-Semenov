package ru.otus.service;

import ru.otus.dao.QuizDao;
import ru.otus.exceptions.DaoException;
import ru.otus.utils.QuizPrinter;

public class QuizService {

    private final QuizDao quizDao;

    private final QuizPrinter quizPrinter;

    private final PrintService printService;

    public QuizService(QuizDao quizCsvDao, QuizPrinter quizPrinter, PrintService printService) {
        this.quizDao = quizCsvDao;
        this.quizPrinter = quizPrinter;
        this.printService = printService;
    }

    public void printQuiz() {
        try {
            quizPrinter.printQuiz(quizDao.getQuestions());
        } catch (DaoException e) {
            printService.println(e.getMessage());
        }
    }
}
