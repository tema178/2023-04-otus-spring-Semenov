package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.DeletedAuthorHasBooksException;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.utils.AuthorPrinter;

@ShellComponent
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AuthorCommands {


    private final AuthorService service;

    private final AuthorPrinter authorPrinter;

    @ShellMethod(value = "Create new author", key = {"createAuthor"})
    public void create(String name) {
        Author author = service.save(new Author(name));
        authorPrinter.print("Author has been created: ", author);
    }

    @ShellMethod(value = "Update author by id", key = {"updateAuthor"})
    public String update(String id, String name) {
        Author author = new Author(id, name);
        service.save(author);
        return "Author has been updated";
    }

    @ShellMethod(value = "Get author by id", key = {"getAuthor"})
    public void get(String id) {
        Author author = service.getById(id);
        authorPrinter.print(author);
    }

    @ShellMethod(value = "Show all authors", key = {"allAuthors"})
    public void all() {
        authorPrinter.print(service.getAll());
    }

    @ShellMethod(value = "Delete author by id", key = {"deleteAuthor"})
    public String delete(String id) {
        try {
            service.deleteById(id);
        } catch (DeletedAuthorHasBooksException e) {
            return "Can't delete author. Library has books with this author";
        }
        return "Author has been deleted";
    }


}
