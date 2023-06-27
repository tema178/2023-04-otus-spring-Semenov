package ru.otus.service;

import org.springframework.stereotype.Service;
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
public class SimpleQuizService implements QuizService {

    private final QuizDao quizDao;

    private final QuizPrinter quizPrinter;

    private final IOService ioService;

    private final QuizResultPrinter quizResultPrinter;

    private final MessageLocalizationService messageLocalizationService;


    public SimpleQuizService(QuizDao quizCsvDao, QuizPrinter quizPrinter, QuizResultPrinter quizResultPrinter,
                             IOService ioService, MessageLocalizationService messageLocalizationService) {
        this.quizDao = quizCsvDao;
        this.quizPrinter = quizPrinter;
        this.ioService = ioService;
        this.quizResultPrinter = quizResultPrinter;
        this.messageLocalizationService = messageLocalizationService;
    }

    public void startQuiz() {
        try {
            Person person = getRespondentData();
            QuizResult quizResult = conductQuiz(person);
            quizResultPrinter.printResult(quizResult);
        } catch (DaoException e) {
            ioService.outputString(messageLocalizationService.getMessage("app.error.1"));
        }
    }

    private Person getRespondentData() {
        String name = ioService.readStringWithPrompt(messageLocalizationService.getMessage("quiz.service.1"));
        String surname = ioService.readStringWithPrompt(messageLocalizationService.getMessage("quiz.service.2"));
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
            String incorrectInput = messageLocalizationService.getMessage("quiz.service.3");
            try {
                numberOfSelectedAnswer = ioService.readIntWithPrompt(
                        messageLocalizationService.getMessage("quiz.service.4"));
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
