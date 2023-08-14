package ru.otus.spring.utils;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.OutputService;

import java.util.List;

@Component
@SuppressWarnings("unused")
public class CommentPrinterImpl implements CommentPrinter {

    private final OutputService outputService;

    public CommentPrinterImpl(OutputService outputService) {
        this.outputService = outputService;
    }

    @Override
    public void print(Comment comment) {
        String format = String.format("- id: %s, %s",
                comment.getId(), comment.getBody());
        outputService.outputString(format);
    }

    @Override
    public void print(String prefix, Comment comment) {
        outputService.outputString(prefix);
        print(comment);
    }

    @Override
    public void print(List<Comment> comments) {
        outputService.outputString("Comments:");
        if (comments.isEmpty()) {
            outputService.outputString("No comments yet");
        }
        comments.sort((b1, b2) -> b1.getId() > b2.getId() ? 1 : -1);
        for (var comment : comments) {
            print(comment);
        }
    }
}
