package ru.otus.spring.shell;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.utils.CommentPrinter;

@ShellComponent
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class CommentCommands {


    private final CommentService commentDao;

    private final CommentPrinter commentPrinter;

    @ShellMethod(value = "Create new comment", key = {"addComment"})
    public void create(long bookId, @NonNull String commentText) {
        Comment comment = commentDao.create(new Comment(bookId, commentText));
        commentPrinter.print("Comment has been created: ", comment);
    }

    @ShellMethod(value = "Update comment by id", key = {"changeComment"})
    public String update(long id, String comment) {
        commentDao.update(id, comment);
        return "Comment has been updated";
    }

    @ShellMethod(value = "Show all comments for book by id", key = {"allComments"})
    public void all(long bookId) {
        commentPrinter.print(commentDao.getAllCommentsForBook(bookId));
    }

    @ShellMethod(value = "Delete comment by id", key = {"deleteComment"})
    public String delete(long id) {
        commentDao.deleteById(id);
        return "Comment has been deleted";
    }


}
