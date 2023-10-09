package ru.otus.spring.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("/api/genre")
    public List<Genre> all() {
        return genreService.getAll();
    }

    @PutMapping("/api/genre")
    public Genre save(@RequestBody GenreDto genreDto) {
        return genreService.save(genreDto.toDomainObject(genreDto));
    }

    @DeleteMapping("/api/genre/{id}")
    public void save(@PathVariable long id) {
        genreService.deleteById(id);
    }
}
