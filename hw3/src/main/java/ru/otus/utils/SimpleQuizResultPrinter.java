package ru.otus.utils;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.config.CountOfAnswersForPassQuizProvider;
import ru.otus.domain.QuizResult;
import ru.otus.service.MessageLocalizationService;
import ru.otus.service.OutputService;

@Component
@PropertySource("application.yaml")
@SuppressWarnings("unused")
public class SimpleQuizResultPrinter implements QuizResultPrinter {

    private final OutputService printService;

    private final int countOfAnswersForPassQuiz;

    private final MessageLocalizationService messageLocalizationService;

    public SimpleQuizResultPrinter(OutputService printService, CountOfAnswersForPassQuizProvider countOfAnswersProvider,
                                   MessageLocalizationService messageLocalizationService) {
        this.printService = printService;
        this.countOfAnswersForPassQuiz = countOfAnswersProvider.getCountOfAnswersForPassQuiz();
        this.messageLocalizationService = messageLocalizationService;
    }

    public void printResult(QuizResult result) {
        printService.outputString(messageLocalizationService.getMessage("quiz.result.1"));
        printService.outputFormatString("%s %s", result.getRespondent().getName(), result.getRespondent().getSurname());
        String quizPassedText = isQuizPassed(result.getCountOfTrueAnswers()) ?
                messageLocalizationService.getMessage("quiz.result.2", String.valueOf(result.getCountOfTrueAnswers())) :
                messageLocalizationService.getMessage("quiz.result.3", String.valueOf(result.getCountOfTrueAnswers()));
        printService.outputString(quizPassedText);
    }

    private boolean isQuizPassed(int countOfTrueAnswers) {
        return countOfTrueAnswers >= countOfAnswersForPassQuiz;
    }
}
