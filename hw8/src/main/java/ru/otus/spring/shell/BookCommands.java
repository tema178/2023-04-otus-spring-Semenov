package ru.otus.spring.shell;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.BookServiceException;
import ru.otus.spring.service.BookService;
import ru.otus.spring.utils.BookPrinter;


@ShellComponent
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class BookCommands {


    private final BookService bookService;

    private final BookPrinter bookPrinter;


    @ShellMethod(value = "Show all books", key = {"allBooks"})
    public void all() {
        bookPrinter.printWithoutComments(bookService.findAll());
    }

    @ShellMethod(value = "Create new book", key = {"createBook"})
    public void create(@NonNull String bookName, @NonNull String authorId,
                       @NonNull String genreId) throws BookServiceException {
        Book book = bookService.create(bookName, authorId, genreId);
        bookPrinter.print("Book has been created: ", book);
    }

    @ShellMethod(value = "Update book by id", key = {"updateBook"})
    public String update(@NonNull String id, @NonNull String bookName,
                         @NonNull String authorId, @NonNull String genreId) {
        try {
            bookService.update(id, bookName, authorId, genreId);
            return "Book has been updated";
        } catch (BookServiceException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Get book by id", key = {"getBook"})
    public void get(String id) {
        Book book = bookService.getById(id);
        bookPrinter.printWithComments(book);
    }

    @ShellMethod(value = "Delete book", key = {"deleteBook"})
    public String deleteBook(String id) {
        bookService.deleteById(id);
        return "Book has been deleted";
    }
}
