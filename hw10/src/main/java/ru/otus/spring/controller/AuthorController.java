package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.spring.service.AuthorService;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;


//    @ShellMethod(value = "Create new author", key = {"createAuthor"})
//    public void create(String name) {
//        Author author = service.save(new Author(name));
//        authorPrinter.print("Author has been created: ", author);
//    }
//
//    @ShellMethod(value = "Update author by id", key = {"updateAuthor"})
//    public String update(long id, String name) {
//        Author author = new Author(id, name);
//        service.save(author);
//        return "Author has been updated";
//    }
//
//    @ShellMethod(value = "Get author by id", key = {"getAuthor"})
//    public void get(long id) {
//        Author author = service.getById(id);
//        authorPrinter.print(author);
//    }
//
//    @ShellMethod(value = "Show all authors", key = {"allAuthors"})
//    public void all() {
//        authorPrinter.print(service.getAll());
//    }
//
//    @ShellMethod(value = "Delete author by id", key = {"deleteAuthor"})
//    public String delete(long id) {
//        service.deleteById(id);
//        return "Author has been deleted";
//    }

}
