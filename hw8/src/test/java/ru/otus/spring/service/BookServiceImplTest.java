package ru.otus.spring.service;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с книгами должен")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
@Import({BookServiceImpl.class})
class BookServiceImplTest {

    private static final String TEST_BOOK_ID = "5";

    private static final String TEST_BOOK_NAME = "Test book";

    private static final String IVAN_ID = "1";

    private static final Author AUTHOR_IVAN = new Author(IVAN_ID, null);

    private static final Author AUTHOR_IVAN_FULL = new Author(IVAN_ID, "Ivan");

    private static final String ACTION_GENRE_ID = "1";

    private static final Genre ACTION_GENRE = new Genre(ACTION_GENRE_ID, null);

    private static final Book BOOK = new Book(TEST_BOOK_ID, TEST_BOOK_NAME, AUTHOR_IVAN, ACTION_GENRE);

    private static final Genre ACTION_GENRE_FULL = new Genre(ACTION_GENRE_ID, "Action");

    private static final Book BOOK_FULL = new Book(TEST_BOOK_ID, TEST_BOOK_NAME, AUTHOR_IVAN_FULL, ACTION_GENRE_FULL);

    private static final String NIKOLAY_ID = "2";

    private static final Author AUTHOR_NIKOLAY_FULL = new Author(NIKOLAY_ID, "Nikolay");

    private static final String FIRST_BOOK_ID = "1";

    private static final String FIRST_BOOK_NAME = "Book of Ivan";

    private static final Book FIRST_BOOK_FULL = new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME,
            AUTHOR_IVAN_FULL, ACTION_GENRE_FULL);

    private static final BookWithComments FIRST_BOOK_FULL_WITH_COMMENTS = new BookWithComments(FIRST_BOOK_ID,
            FIRST_BOOK_NAME, AUTHOR_IVAN_FULL, ACTION_GENRE_FULL,
            List.of(new Comment(ObjectId.get().toString(), "Comment 1")));


    private static final String SECOND_BOOK_ID = "2";

    private static final String SECOND_BOOK_NAME = "Book of Nikolay";

    private static final Book SECOND_BOOK_FULL = new Book(SECOND_BOOK_ID,
            SECOND_BOOK_NAME, AUTHOR_NIKOLAY_FULL, ACTION_GENRE_FULL);

    private static final int EXPECTED_LIBRARY_SIZE = 2;

    @Autowired
    @SuppressWarnings("unused")
    private BookService bookService;

    @MockBean
    @SuppressWarnings("unused")
    private BookRepository bookRepository;

    @MockBean
    @SuppressWarnings("unused")
    private BookWithCommentsRepository bookWithCommentsRepository;

    @MockBean
    @SuppressWarnings("unused")
    private AuthorRepository authorRepository;

    @MockBean
    @SuppressWarnings("unused")
    private GenreRepository genreRepository;


    @DisplayName("Создать новую книгу")
    @Test
    void shouldInsertNewBook() throws BookServiceException {
        given(bookRepository.save(any())).willReturn(BOOK_FULL);
        given(authorRepository.findById(IVAN_ID)).willReturn(Optional.of(AUTHOR_IVAN_FULL));
        given(genreRepository.findById(ACTION_GENRE_ID)).willReturn(Optional.of(ACTION_GENRE_FULL));
        Book result = bookService.create(TEST_BOOK_NAME, IVAN_ID, ACTION_GENRE_ID);
        assertThat(result.getName()).isEqualTo(BOOK.getName());
        assertThat(result.getAuthor()).isEqualTo(BOOK_FULL.getAuthor());
        assertThat(result.getGenre()).isEqualTo(BOOK_FULL.getGenre());
    }

    @DisplayName("Получить книгу по id")
    @Test
    void shouldGetBookById() {
        given(bookWithCommentsRepository.findById(FIRST_BOOK_ID))
                .willReturn(Optional.of(FIRST_BOOK_FULL_WITH_COMMENTS));
        BookWithComments book = bookService.getById(FIRST_BOOK_ID);
        assertThat(book.getName()).isEqualTo(FIRST_BOOK_FULL.getName());
        assertThat(book.getAuthor()).isEqualTo(FIRST_BOOK_FULL.getAuthor());
        assertThat(book.getGenre()).isEqualTo(FIRST_BOOK_FULL.getGenre());
    }

    @DisplayName("Получить все книги")
    @Test
    void shouldGetBooks() {
        given(bookRepository.findAll()).willReturn(
                List.of(FIRST_BOOK_FULL, SECOND_BOOK_FULL));
        List<Book> book = bookService.findAll();
        assertThat(book).hasSize(EXPECTED_LIBRARY_SIZE).isEqualTo(
                List.of(FIRST_BOOK_FULL, SECOND_BOOK_FULL));
    }
}
