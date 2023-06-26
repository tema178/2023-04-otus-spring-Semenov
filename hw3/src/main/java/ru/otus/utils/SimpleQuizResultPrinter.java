package ru.otus.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.config.CountOfAnswersForPassQuizProvider;
import ru.otus.config.LocaleProvider;
import ru.otus.domain.QuizResult;
import ru.otus.service.OutputService;

@Component
@PropertySource("application.yaml")
@SuppressWarnings("unused")
public class SimpleQuizResultPrinter implements QuizResultPrinter {

    private final OutputService printService;

    private final int countOfAnswersForPassQuiz;

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public SimpleQuizResultPrinter(OutputService printService, CountOfAnswersForPassQuizProvider countOfAnswersProvider,
                                   MessageSource messageSource, LocaleProvider localeProvider) {
        this.printService = printService;
        this.countOfAnswersForPassQuiz = countOfAnswersProvider.getCountOfAnswersForPassQuiz();
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    public void printResult(QuizResult result) {
        printService.outputString(messageSource.getMessage("quiz.result.1",null,localeProvider.getLocale()));
        printService.outputFormatString("%s %s", result.getRespondent().getName(), result.getRespondent().getSurname());
        String quizPassedText = isQuizPassed(result.getCountOfTrueAnswers()) ?
                messageSource.getMessage(
                        "quiz.result.2", new Integer[] {result.getCountOfTrueAnswers()},localeProvider.getLocale()) :
                messageSource.getMessage(
                        "quiz.result.3",new Integer[] {result.getCountOfTrueAnswers()},localeProvider.getLocale());
        printService.outputString(quizPassedText);
    }

    private boolean isQuizPassed(int countOfTrueAnswers) {
        return countOfTrueAnswers >= countOfAnswersForPassQuiz;
    }
}
