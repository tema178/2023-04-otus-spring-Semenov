package ru.otus.spring.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService service;

    @GetMapping("/api/author")
    public List<Author> all() {
        return service.getAll();
    }

    @PutMapping("/api/author")
    public Author save(@RequestBody AuthorDto authorDto) {
        return service.save(authorDto.toDomainObject(authorDto));
    }

    @DeleteMapping("/api/author/{id}")
    public void save(@PathVariable long id) {
        service.deleteById(id);
    }
}
