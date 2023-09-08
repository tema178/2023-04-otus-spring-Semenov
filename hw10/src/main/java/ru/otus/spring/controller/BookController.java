package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BookController {

    @GetMapping({"/", "/book"})
    public String all() {
        return "bookList";
    }

    @GetMapping({"/book/create"})
    public String edit() {
        return "bookCreate";
    }

    @GetMapping("/book/{id}")
    public String get() {
        return "bookEdit";
    }
}
