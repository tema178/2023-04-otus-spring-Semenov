package ru.otus.spring.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.BookServiceException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@Component
@Slf4j
public class BookServiceImpl implements BookService {

    public static final String BOOK_SERVICE = "bookService";

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @CircuitBreaker(name = BOOK_SERVICE, fallbackMethod = "bulkheadSave")
    @Bulkhead(name = BOOK_SERVICE, fallbackMethod = "bulkheadSave")
    @Transactional
    @Override
    public Book save(Book book) throws BookServiceException {
        book.setAuthor(getAuthor(book));
        book.setGenre(getGenre(book));
        return bookRepository.save(book);
    }

    @CircuitBreaker(name = BOOK_SERVICE, fallbackMethod = "bulkheadGetAll")
    @Bulkhead(name = BOOK_SERVICE, fallbackMethod = "bulkheadGetAll")
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @CircuitBreaker(name = BOOK_SERVICE, fallbackMethod = "bulkheadGetById")
    @Bulkhead(name = BOOK_SERVICE, fallbackMethod = "bulkheadGetById")
    @Transactional(readOnly = true)
    @Override
    @SuppressWarnings("unused")
    public Book getById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        book.setComments(commentRepository.getCommentsByBookId(id));
        return book;
    }

    @CircuitBreaker(name = BOOK_SERVICE, fallbackMethod = "bulkheadDelete")
    @Bulkhead(name = BOOK_SERVICE, fallbackMethod = "bulkheadDelete")
    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Genre getGenre(@NonNull Book book) throws BookServiceException {
        if (book.getGenre() == null) {
            throw new BookServiceException("Genre not found");
        }
        return genreRepository.findById(book.getGenre().getId()).
                orElseThrow(() -> new BookServiceException("Genre not found"));
    }

    private Author getAuthor(@NonNull Book book) throws BookServiceException {
        if (book.getAuthor() == null) {
            throw new BookServiceException("Author not found");
        }
        return authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new BookServiceException("Author not found"));
    }

    private List<Book> bulkheadGetAll(Exception e) {
        log.info("Bulkhead get all book");
        return List.of(new Book("no name", new Author(0, "no name"), new Genre(0, "no name")));
    }

    private Book bulkheadGetById(long id, Exception e) {
        log.info("Bulkhead get book");
        return new Book("no name", new Author(0, "no name"), new Genre(0, "no name"));
    }

    private Book bulkheadSave(Genre genre, Exception e) {
        log.info("Bulkhead save book");
        return new Book("no name", new Author(0, "no name"), new Genre(0, "no name"));
    }

    private void bulkheadDelete(long id, Exception e) {
        log.info("Bulkhead delete book");
    }

}
