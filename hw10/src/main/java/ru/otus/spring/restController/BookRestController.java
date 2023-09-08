package ru.otus.spring.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.exceptions.BookServiceException;
import ru.otus.spring.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookRestController {

    private final BookService bookService;

    @GetMapping({"/api/book"})
    public List<BookDto> all() {
        List<Book> books = bookService.getAll();
        return books.stream().map(BookDto::toDto).toList();
    }

    @PutMapping("/api/book")
    public BookDto save(@RequestBody BookDto book) throws BookServiceException {
        return BookDto.toDto(bookService.save(book.toDomainObject()));
    }

    @GetMapping("/api/book/{id}")
    public BookDto get(@PathVariable("id") long id) {
        return BookDto.toDto(bookService.getById(id));
    }

    @DeleteMapping("/api/book/{id}")
    public void delete(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }
}
