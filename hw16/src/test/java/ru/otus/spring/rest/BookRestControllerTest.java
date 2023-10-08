package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.restController.BookRestController;
import ru.otus.spring.service.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

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

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldReturnBooksList() throws Exception {

        List<Book> books = List.of(FIRST_BOOK_FULL, SECOND_BOOK_FULL);
        given(bookService.getAll()).willReturn(books);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/book"));
        perform.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books)));
    }

    @Test
    void shouldReturnBookById() throws Exception {
        given(bookService.getById(FIRST_BOOK_ID)).willReturn(FIRST_BOOK_FULL);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/book/1"));
        perform.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(BookDto.toDto(FIRST_BOOK_FULL))));
    }

    @Test
    void shouldHaveDeleteEndpoint() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/1"));
        perform.andExpect(status().isOk());
    }

    @Test
    void shouldReturnBookDtoAfterSaving() throws Exception {
        given(bookService.save(any())).willReturn(FIRST_BOOK_FULL);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.put("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(BookDto.toDto(FIRST_BOOK_FULL))));
        perform.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(BookDto.toDto(FIRST_BOOK_FULL))));
    }
}
