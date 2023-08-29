package ru.otus.spring.utils;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.OutputService;

import java.util.Comparator;
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
                comment.getId(), comment.getText());
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
        comments.sort(Comparator.comparing(Comment::getText));
        for (var comment : comments) {
            print(comment);
        }
    }
}
