package ru.otus.spring.shell;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.BookService;

@ShellComponent
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class CommentCommands {

    private final BookService bookService;

    @ShellMethod(value = "Add a comment", key = {"addComment"})
    public String create(String bookId, @NonNull String commentText) {
        bookService.addComment(bookId, new Comment(commentText));
        return "Comment has been added";
    }

    @ShellMethod(value = "Delete comment", key = {"deleteComment"})
    public String delete(@NonNull String bookId, @NonNull String commentId) {
        bookService.deleteComment(bookId, commentId);
        return "Comment has been deleted";
    }

    @ShellMethod(value = "Change comment", key = {"changeComment"})
    public String update(@NonNull String bookId, @NonNull String commentId, @NonNull String text) {
        bookService.updateComment(bookId, new Comment(commentId, text));
        return "Comment has been updated";
    }
}
