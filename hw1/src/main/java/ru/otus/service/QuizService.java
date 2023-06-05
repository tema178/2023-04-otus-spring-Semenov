package ru.otus.service;

import ru.otus.dao.QuizDao;
import ru.otus.utils.QuizPrinter;

import java.io.IOException;

public class QuizService {
    
    private final QuizDao quizDao;

    private final QuizPrinter quizPrinter;


    public QuizService(QuizDao quizDao, QuizPrinter quizPrinter) {
        this.quizDao = quizDao;
        this.quizPrinter = quizPrinter;
    }

    public void printQuiz() throws IOException {
        quizPrinter.printQuiz(quizDao.getQuestions());
    }
}
