package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.BookWithComments;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.BookWithCommentsRepository;
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

    private final BookWithCommentsRepository bookWithCommentsRepository;


    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           GenreRepository genreRepository,
                           BookWithCommentsRepository bookWithCommentsRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookWithCommentsRepository = bookWithCommentsRepository;
    }

    @Override
    public Book create(String bookName, String authorId, String genreId) throws BookServiceException {
        Author author = getAuthor(authorId);
        Genre genre = getGenre(genreId);
        Book book = new Book(bookName, author, genre);
        return bookRepository.save(book);
    }

    @Override
    public void update(String id, String bookName, String authorId, String genreId) throws BookServiceException {
        Author author = getAuthor(authorId);
        Genre genre = getGenre(genreId);
        Book book = new Book(id, bookName, author, genre);
        bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @SuppressWarnings("unused")
    public BookWithComments getById(String id) {
        return bookWithCommentsRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void addComment(String bookId, Comment comment) {
        BookWithComments book = bookWithCommentsRepository.findById(bookId).orElseThrow();
        book.getComments().add(comment);
        bookWithCommentsRepository.save(book);
    }

    @Override
    public void deleteComment(String bookId, String commentId) {
        BookWithComments book = bookWithCommentsRepository.findById(bookId).orElseThrow();
        List<Comment> comments = book.getComments();
        List<Comment> collect = comments.stream().filter(comment -> !comment.getId().equals(commentId)).toList();
        book.setComments(collect);
        bookWithCommentsRepository.save(book);
    }

    @Override
    public void updateComment(String bookId, Comment newComment) {
        BookWithComments book = bookWithCommentsRepository.findById(bookId).orElseThrow();
        List<Comment> comments = book.getComments();
        Comment matchedComment = comments.stream().filter(comment -> comment.getId().equals(newComment.getId()))
                .findFirst().orElseThrow();
        matchedComment.setText(newComment.getText());
        bookWithCommentsRepository.save(book);
    }

    private Genre getGenre(String genreId) throws BookServiceException {
        return genreRepository.findById(genreId).orElseThrow(() -> new BookServiceException("Genre not found"));
    }

    private Author getAuthor(String authorId) throws BookServiceException {
        return authorRepository.findById(authorId).orElseThrow(() -> new BookServiceException("Author not found"));
    }

}
