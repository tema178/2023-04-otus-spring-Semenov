package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.BookServiceException;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.InputService;
import ru.otus.spring.service.OutputService;
import ru.otus.spring.utils.BookPrinter;


@ShellComponent
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class BookCommands {


    private final BookService quizService;

    private final InputService inputService;

    private final OutputService outputService;

    private final BookPrinter bookPrinter;


    @ShellMethod(value = "Show all books", key = {"allBooks"})
    public void all() {
        bookPrinter.print(quizService.getAll());
    }

    @ShellMethod(value = "Create new book", key = {"createBook"})
    public String create() {
        String bookName = inputService.readStringWithPrompt("Enter book name:");
        long authorId = inputService.readLongWithPrompt("Enter author id:");
        long genreId = inputService.readLongWithPrompt("Enter genre id:");
        try {
            Book book = quizService.create(bookName, authorId, genreId);
            bookPrinter.print("Book has been created: ", book);
            return null;
        } catch (BookServiceException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Update book by id", key = {"updateBook"})
    public String update() {
        long id = inputService.readLongWithPrompt("Enter book id:");
        String bookName = inputService.readStringWithPrompt("Enter book name:");
        long authorId = inputService.readLongWithPrompt("Enter author id:");
        long genreId = inputService.readLongWithPrompt("Enter genre id:");
        try {
            quizService.update(id, bookName, authorId, genreId);
            return "Book has been updated";
        } catch (BookServiceException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Get book by id", key = {"getBook"})
    public void get(long id) {
        Book book = quizService.getById(id);
        bookPrinter.printWithComments(book);
    }

    @ShellMethod(value = "Delete book", key = {"deleteBook"})
    public String deleteBook(long id) {
        quizService.deleteById(id);
        return "Book has been deleted";
    }
}
