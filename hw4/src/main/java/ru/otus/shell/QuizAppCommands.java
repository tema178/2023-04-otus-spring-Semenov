package ru.otus.shell;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.domain.Person;
import ru.otus.service.MessageLocalizationService;
import ru.otus.service.QuizService;

@ShellComponent
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class QuizAppCommands {

    private Person person;

    private final QuizService quizService;

    private final MessageLocalizationService messageLocalizationService;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@NonNull String name, @NonNull String surname) {
        this.person = new Person(name, surname);
        return messageLocalizationService.getMessage("quiz.command.1", name);
    }

    @ShellMethod(value = "Start quiz", key = {"s", "start"})
    @ShellMethodAvailability(value = "isLogin")
    public void start() {
        quizService.startQuiz(person);
    }

    private Availability isLogin() {
        return person == null ?
                Availability.unavailable(messageLocalizationService.getMessage("quiz.command.2")) :
                Availability.available();
    }
}
