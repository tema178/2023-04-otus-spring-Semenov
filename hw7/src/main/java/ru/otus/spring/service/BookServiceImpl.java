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
        Author author = getAuthor(authorId);
        Genre genre = getGenre(genreId);
        Book book = new Book(bookName, author, genre);
        return bookDao.save(book);
    }

    @Transactional
    @Override
    public void update(long id, String bookName, long authorId, long genreId) throws BookServiceException {
        Author author = getAuthor(authorId);
        Genre genre = getGenre(genreId);
        Book book = new Book(id, bookName, author, genre);
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
        return bookDao.getByIdWithInitializedComments(id).orElseThrow(
                () -> new EntityNotFoundException(entityNotFoundExceptionMessageFormat("Book", id)));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    private Genre getGenre(long genreId) throws BookServiceException {
        return genreDao.findById(genreId).orElseThrow(() -> new BookServiceException("Genre not found"));
    }

    private Author getAuthor(long authorId) throws BookServiceException {
        return authorDao.findById(authorId).orElseThrow(() -> new BookServiceException("Author not found"));
    }

}
