package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final long IVAN_ID = 1;

    private static final Author AUTHOR_IVAN_FULL = new Author(IVAN_ID, "Ivan");

    private static final long ACTION_GENRE_ID = 1;

    private static final Genre ACTION_GENRE_FULL = new Genre(ACTION_GENRE_ID, "Action");

    private static final int NIKOLAY_ID = 2;

    private static final Author AUTHOR_NIKOLAY_FULL = new Author(NIKOLAY_ID, "Nikolay");

    private static final long FIRST_BOOK_ID = 1;

    private static final String FIRST_BOOK_NAME = "Book of Ivan";

    private static final Book FIRST_BOOK_FULL = new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME,
            AUTHOR_IVAN_FULL, ACTION_GENRE_FULL);


    private static final int SECOND_BOOK_ID = 2;

    private static final String SECOND_BOOK_NAME = "Book of Nikolay";

    private static final Book SECOND_BOOK_FULL = new Book(SECOND_BOOK_ID, SECOND_BOOK_NAME,
            AUTHOR_NIKOLAY_FULL, ACTION_GENRE_FULL);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @Test
    void shouldReturnBooksList() throws Exception {
        List<Book> books = List.of(FIRST_BOOK_FULL, SECOND_BOOK_FULL);
        given(bookService.getAll()).willReturn(books);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/"));
        perform.andExpect(status().isOk()).
                andExpect(model().attributeExists("books"));
    }

    @Test
    void shouldReturnBookById() throws Exception {
        given(bookService.getById(FIRST_BOOK_ID)).willReturn(FIRST_BOOK_FULL);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/book/1"));
        perform.andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attributeExists("commentCreate"));
    }

    @Test
    void shouldReturnEditModelInfo() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/book/edit"));
        perform.andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"));
    }

    @Test
    void shouldRedirectToRootAfterBookCreating() throws Exception {
        given(bookService.save(FIRST_BOOK_FULL)).willReturn(FIRST_BOOK_FULL);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/book"));
        perform.andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    void shouldRedirectToRootAfterBookDeletion() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/book/1/delete"));
        perform.andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }
}
