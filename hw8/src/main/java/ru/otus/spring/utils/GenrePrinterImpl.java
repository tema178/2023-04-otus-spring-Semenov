package ru.otus.spring.utils;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.OutputService;

import java.util.Comparator;
import java.util.List;

@Component
@SuppressWarnings("unused")
public class GenrePrinterImpl implements GenrePrinter {

    private final OutputService outputService;

    public GenrePrinterImpl(OutputService outputService) {
        this.outputService = outputService;
    }

    @Override
    public void print(Genre genre) {
        String format = String.format("id: %s, name: %s",
                genre.getId(), genre.getName());
        outputService.outputString(format);
    }

    @Override
    public void print(String prefix, Genre genre) {
        outputService.outputString(prefix);
        print(genre);
    }

    @Override
    public void print(List<Genre> genres) {
        genres.sort(Comparator.comparing(Genre::getName));
        for (var genre : genres) {
            print(genre);
        }
    }
}
