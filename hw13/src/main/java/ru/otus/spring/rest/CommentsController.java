package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CommentsController {

    @GetMapping("/comment/{id}")
    public String edit() {
        return "commentEdit";
    }
}
