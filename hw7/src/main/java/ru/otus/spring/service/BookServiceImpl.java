package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
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

import static ru.otus.spring.exceptions.ExceptionUtil.entityNotFoundExceptionMessageFormat;

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
    public Book create(String bookName, long authorId, long genreId) throws BookServiceException {
        Author author = getAuthor(authorId);
        Genre genre = getGenre(genreId);
        Book book = new Book(bookName, author, genre);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void update(long id, String bookName, long authorId, long genreId) throws BookServiceException {
        Author author = getAuthor(authorId);
        Genre genre = getGenre(genreId);
        Book book = new Book(id, bookName, author, genre);
        bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    @SuppressWarnings("unused")
    public Book getById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(entityNotFoundExceptionMessageFormat("Book", id)));
        book.setComments(commentRepository.getAllCommentsForBook(id));
        return book;
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Genre getGenre(long genreId) throws BookServiceException {
        return genreRepository.findById(genreId).orElseThrow(() -> new BookServiceException("Genre not found"));
    }

    private Author getAuthor(long authorId) throws BookServiceException {
        return authorRepository.findById(authorId).orElseThrow(() -> new BookServiceException("Author not found"));
    }

}
