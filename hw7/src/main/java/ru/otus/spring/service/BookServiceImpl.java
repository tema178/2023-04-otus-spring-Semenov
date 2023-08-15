package ru.otus.spring.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.BookServiceException;

import java.util.List;
import java.util.Optional;

import static ru.otus.spring.exceptions.ExceptionUtil.entityNotFoundExceptionMessageFormat;

@Component
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Transactional
    @Override
    public Book create(String bookName, long authorId, long genreId) throws BookServiceException {
        Optional<Author> author = authorDao.findById(authorId);
        Optional<Genre> genre = genreDao.findById(genreId);
        validateAuthorAndGenre(author, genre);
        Book book = new Book(bookName, author.get(), genre.get());
        return bookDao.save(book);
    }

    @Transactional
    @Override
    public void update(long id, String bookName, long authorId, long genreId) throws BookServiceException {
        Optional<Author> author = authorDao.findById(authorId);
        Optional<Genre> genre = genreDao.findById(genreId);
        validateAuthorAndGenre(author, genre);
        Book book = new Book(id, bookName, author.get(), genre.get());
        bookDao.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.findAll();
    }

    @Transactional
    @Override
    @SuppressWarnings("unused")
    public Book getById(long id) {
        Optional<Book> bookOptional = bookDao.getByIdWithInitializedComments(id);
        if (bookOptional.isEmpty()) {
            throw new EntityNotFoundException(entityNotFoundExceptionMessageFormat("Book", id));
        }
        return bookOptional.get();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    private void validateAuthorAndGenre(Optional<Author> author, Optional<Genre> genre) throws BookServiceException {
        if (author.isEmpty()) {
            throw new BookServiceException("Author not found");
        }
        if (genre.isEmpty()) {
            throw new BookServiceException("Genre not found");
        }
    }
}
