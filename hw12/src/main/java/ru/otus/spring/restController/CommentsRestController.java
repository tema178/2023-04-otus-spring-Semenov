package ru.otus.spring.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentsRestController {

    private final CommentService commentService;

    @PutMapping("/api/comment")
    public CommentDto save(@RequestBody CommentDto comment) {
        return CommentDto.toDto(commentService.save(CommentDto.toDomainObject(comment)));
    }

    @GetMapping("/api/comment/{id}")
    public CommentDto get(@PathVariable long id) {
        return CommentDto.toDto(commentService.findById(id));
    }

    @DeleteMapping("/api/comment/{id}")
    public void delete(@PathVariable long id) {
        commentService.deleteById(id);
    }
}
