package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.BookServiceException;

import java.util.List;

@Component
public class BookServiceImpl implements BookService {

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

    @Transactional
    @Override
    public Book save(Book book) throws BookServiceException {
        book.setAuthor(getAuthor(book));
        book.setGenre(getGenre(book));
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    @SuppressWarnings("unused")
    public Book getById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        book.setComments(commentRepository.getCommentsByBookId(id));
        return book;
    }

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

}
