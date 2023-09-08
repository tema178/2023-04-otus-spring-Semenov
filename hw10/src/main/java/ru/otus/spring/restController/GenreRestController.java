package ru.otus.spring.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreService genreService;

//    @PostMapping("/genres")
//    public void create(String name) {
//        genreService.save(new Genre(name));
//    }
//
//    @PostMapping("/genres/edit")
//    public String update(long id, String name) {
//        Genre genre = new Genre(id, name);
//        genreService.save(genre);
//        return "Genre has been updated";
//    }
//
//    @GetMapping("/genres/get")
//    public void get(@RequestParam("id") long id, Model model) {
//        genreService.getById(id);
//    }

    @GetMapping("/api/genre")
    public List<Genre> all() {
        return genreService.getAll();
    }


//    @DeleteMapping("/genres")
//    public String delete(@RequestParam("id") long id) {
//        genreService.deleteById(id);
//        return "Genre has been deleted";
//    }
}
