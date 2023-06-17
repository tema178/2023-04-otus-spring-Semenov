package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.QuizDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Person;
import ru.otus.domain.Quiz;
import ru.otus.domain.QuizResult;
import ru.otus.exceptions.DaoException;
import ru.otus.utils.QuizPrinter;
import ru.otus.utils.SimpleQuizResultPrinter;

import java.util.List;

@Service
public class QuizService {

    private final QuizDao quizDao;

    private final QuizPrinter quizPrinter;

    private final IOServiceStreams ioServiceStreams;

    private final SimpleQuizResultPrinter simpleQuizResultPrinter;

    public QuizService(QuizDao quizCsvDao, QuizPrinter quizPrinter, IOServiceStreams ioServiceStreams,
                       SimpleQuizResultPrinter simpleQuizResultPrinter) {
        this.quizDao = quizCsvDao;
        this.quizPrinter = quizPrinter;
        this.ioServiceStreams = ioServiceStreams;
        this.simpleQuizResultPrinter = simpleQuizResultPrinter;
    }

    public void startQuiz() {
        try {
            Person person = getRespondentData();
            QuizResult quizResult = conductQuiz(person);
            simpleQuizResultPrinter.printResult(quizResult);
        } catch (DaoException e) {
            ioServiceStreams.outputFormatString("Application error: %s", e.getMessage());
        }
    }

    private Person getRespondentData() {
        String name = ioServiceStreams.readStringWithPrompt("Input your name: ");
        String surname = ioServiceStreams.readStringWithPrompt("Input your surname: ");
        return new Person(name, surname);
    }

    private QuizResult conductQuiz(Person person) throws DaoException {
        List<Quiz> quizList = quizDao.getQuestions();
        int countOfCorrectAnswers = 0;
        for (var quiz : quizList) {
            quizPrinter.printQuiz(quiz);
            int userAnswer = readUserAnswer(quiz);
            if (isUserAnswerCorrect(userAnswer, quiz.getAnswers())) {
                countOfCorrectAnswers++;
            }
        }
        return new QuizResult(quizList, person, countOfCorrectAnswers);
    }

    private boolean isUserAnswerCorrect(int userAnswer, List<Answer> answers) {
        return answers.get(userAnswer - 1).isCorrect();
    }

    private int readUserAnswer(Quiz quiz) {
        int numberOfSelectedAnswer = 0;
        do {
            String incorrectInput = "Incorrect value. Please, try again: ";
            try {
                numberOfSelectedAnswer = ioServiceStreams.readIntWithPrompt("Input number of true answer: ");
            } catch (NumberFormatException e) {
                ioServiceStreams.outputString(incorrectInput);
            }
            if (numberOfAnswerInRange(quiz.getAnswers(), numberOfSelectedAnswer)) {
                ioServiceStreams.outputString(incorrectInput);
            }
        } while (numberOfAnswerInRange(quiz.getAnswers(), numberOfSelectedAnswer));
        return numberOfSelectedAnswer;
    }

    private boolean numberOfAnswerInRange(List<Answer> answers, int numberOfSelectedAnswer) {
        return !(numberOfSelectedAnswer > 0 && numberOfSelectedAnswer <= answers.size());
    }
}
