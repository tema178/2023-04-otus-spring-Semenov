package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.BookServiceException;

import java.util.List;

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
        Author author = authorDao.getById(authorId);
        Genre genre = genreDao.getById(genreId);
        validateAuthorAndGenre(author, genre);
        Book book = new Book(bookName, author, genre);
        return bookDao.create(book);
    }

    @Transactional
    @Override
    public boolean update(long id, String bookName, long authorId, long genreId) throws BookServiceException {
        Author author = authorDao.getById(authorId);
        Genre genre = genreDao.getById(genreId);
        validateAuthorAndGenre(author, genre);
        Book book = new Book(id, bookName, author, genre);
        return bookDao.update(book) > 0;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Transactional
    @Override
    @SuppressWarnings("unused")
    public Book getById(long id) {
        Book book = bookDao.getById(id);
        List<Comment> comments = book.getComments();
        return book;
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        return bookDao.deleteById(id) > 0;
    }

    private void validateAuthorAndGenre(Author author, Genre genre) throws BookServiceException {
        if (author == null) {
            throw new BookServiceException("Author not found");
        }
        if (genre == null) {
            throw new BookServiceException("Genre not found");
        }
    }
}
