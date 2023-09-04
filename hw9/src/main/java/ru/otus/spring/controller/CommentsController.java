package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public String create(Comment comment, @RequestParam long bookId) {
        Book book = new Book();
        book.setId(bookId);
        comment.setBook(book);
        commentService.create(comment);
        return "redirect:/book/" + bookId;
    }

    @GetMapping("/comment/{id}")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("comment", commentService.findById(id));
        return "commentEdit";
    }

    @PostMapping("comment/{id}/delete")
    public String delete(@PathVariable long id) {
        commentService.deleteById(id);
        return "redirect:/";
    }
}
