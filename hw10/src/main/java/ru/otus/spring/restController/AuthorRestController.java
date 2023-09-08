package ru.otus.spring.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

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

    @GetMapping("/api/author")
    public List<Author> all() {
        return service.getAll();
    }
//
//    @ShellMethod(value = "Delete author by id", key = {"deleteAuthor"})
//    public String delete(long id) {
//        service.deleteById(id);
//        return "Author has been deleted";
//    }

}
