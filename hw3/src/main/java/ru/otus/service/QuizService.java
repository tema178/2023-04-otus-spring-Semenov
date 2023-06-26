package ru.otus.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.LocaleProvider;
import ru.otus.dao.QuizDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Person;
import ru.otus.domain.Quiz;
import ru.otus.domain.QuizResult;
import ru.otus.exceptions.DaoException;
import ru.otus.utils.QuizPrinter;
import ru.otus.utils.QuizResultPrinter;

import java.util.List;

@Service
@SuppressWarnings("unused")
public class QuizService implements QuizServiceInterface{

    private final QuizDao quizDao;

    private final QuizPrinter quizPrinter;

    private final IOService ioService;

    private final QuizResultPrinter quizResultPrinter;

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public QuizService(QuizDao quizCsvDao, QuizPrinter quizPrinter, IOService ioService,
                       QuizResultPrinter quizResultPrinter, MessageSource messageSource,
                       LocaleProvider localeProvider) {
        this.quizDao = quizCsvDao;
        this.quizPrinter = quizPrinter;
        this.ioService = ioService;
        this.quizResultPrinter = quizResultPrinter;
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    public void startQuiz() {
        try {
            Person person = getRespondentData();
            QuizResult quizResult = conductQuiz(person);
            quizResultPrinter.printResult(quizResult);
        } catch (DaoException e) {
            ioService.outputString(messageSource.getMessage(
                    "app.error.1", new String [] {e.getMessage()}, localeProvider.getLocale()));
        }
    }

    private Person getRespondentData() {
        String name = ioService.readStringWithPrompt(messageSource.getMessage(
                "quiz.service.1", null, localeProvider.getLocale()));
        String surname = ioService.readStringWithPrompt(messageSource.getMessage(
                "quiz.service.2", null, localeProvider.getLocale()));
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
            String incorrectInput = messageSource.getMessage(
                    "quiz.service.3", null, localeProvider.getLocale());
            try {
                numberOfSelectedAnswer = ioService.readIntWithPrompt(messageSource.getMessage(
                        "quiz.service.4", null, localeProvider.getLocale()));
            } catch (NumberFormatException e) {
                ioService.outputString(incorrectInput);
                continue;
            }
            if (numberOfAnswerInRange(quiz.getAnswers(), numberOfSelectedAnswer)) {
                ioService.outputString(incorrectInput);
            }
        } while (numberOfAnswerInRange(quiz.getAnswers(), numberOfSelectedAnswer));
        return numberOfSelectedAnswer;
    }

    private boolean numberOfAnswerInRange(List<Answer> answers, int numberOfSelectedAnswer) {
        return !(numberOfSelectedAnswer > 0 && numberOfSelectedAnswer <= answers.size());
    }
}
