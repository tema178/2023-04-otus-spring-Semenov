package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/genres")
    public void create(String name) {
        genreService.save(new Genre(name));
    }

    @PostMapping("/genres/edit")
    public String update(long id, String name) {
        Genre genre = new Genre(id, name);
        genreService.save(genre);
        return "Genre has been updated";
    }

    @GetMapping("/genres/get")
    public void get(@RequestParam("id") long id, Model model) {
        genreService.getById(id);
    }

    @GetMapping("/genres")
    public String all(Model model) {
        List<Genre> all = genreService.getAll();
        model.addAttribute("genres", all);
        return "list";
    }


    @DeleteMapping("/genres")
    public String delete(@RequestParam("id") long id) {
        genreService.deleteById(id);
        return "Genre has been deleted";
    }
}
