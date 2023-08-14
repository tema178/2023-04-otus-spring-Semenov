package ru.otus.spring.utils;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.OutputService;

import java.util.List;

@Component
@SuppressWarnings("unused")
public class BookPrinterImpl implements BookPrinter {

    private final OutputService outputService;

    private final CommentPrinter commentPrinter;

    public BookPrinterImpl(OutputService outputService, CommentPrinter commentPrinter) {
        this.outputService = outputService;
        this.commentPrinter = commentPrinter;
    }

    @Override
    public void printWithComments(Book book) {
        String format = String.format("id: %s, name: %s, author: %s, genre: %s",
                book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        outputService.outputString(format);
        commentPrinter.print(book.getComments());
    }

    @Override
    public void print(Book book) {
        String format = String.format("id: %s, name: %s, author: %s, genre: %s",
                book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        outputService.outputString(format);
    }

    @Override
    public void print(String prefix, Book book) {
        outputService.outputString(prefix);
        print(book);
    }

    @Override
    public void print(List<Book> books) {
        books.sort((b1, b2) -> b1.getId() > b2.getId() ? 1 : -1);
        for (var book : books) {
            print(book);
        }
    }
}
