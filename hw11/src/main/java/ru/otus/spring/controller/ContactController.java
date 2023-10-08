package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ContactController {

    @GetMapping("/")
    public String startPage() {
        return "contactList";
    }

    @GetMapping("/contact/{id}")
    public String contactEdit() {
        return "contactEdit";
    }

    @GetMapping("/contact/create")
    public String contactCreate() {
        return "contactCreate";
    }
}
