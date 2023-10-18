package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.restController.AuthorRestController;
import ru.otus.spring.restController.BookRestController;
import ru.otus.spring.restController.CommentsRestController;
import ru.otus.spring.restController.GenreRestController;
import ru.otus.spring.restController.UserRestController;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SecurityConfiguration.class,
        BookRestController.class,
        CommentsRestController.class,
        GenreRestController.class,
        AuthorRestController.class,
        UserRestController.class})
class AllRestControllersAuthorizeTest {

    public static final String LOGIN = "http://localhost/login";

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void bookApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/1")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/1").with(csrf()))
                .andExpect(status().isFound()).andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/book").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("some book")).andExpect(status().isFound()).andExpect(redirectedUrl(LOGIN));
    }

    @Test
    @WithMockUser(username = "admin")
    void bookApiTestAuthorized() throws Exception {
        given(bookService.getById(1)).willReturn(new Book());
        given(bookService.getAll()).willReturn(List.of(new Book()));
        given(bookService.save(any())).willReturn(new Book());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/1")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/1").with(csrf()))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/book").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Book()))).andExpect(status().isOk());
    }

    @Test
    void commentApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/1")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/comment/1")
                .with(csrf())).andExpect(status().isFound()).andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/comment").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("some comment")).andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "admin")
    void commentApiTestAuthorized() throws Exception {
        given(commentService.findById(1)).willReturn(new Comment(1, "some comment"));
        given(commentService.save(any())).willReturn(new Comment(1, "some comment"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/1")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/comment/1")
                .with(csrf())).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/comment").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Comment()))).andExpect(status().isOk());
    }

    @Test
    void genreApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/genre")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
    }

    @Test
    @WithMockUser(username = "admin")
    void genreApiTestAuthorized() throws Exception {
        given(genreService.getAll()).willReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/genre")).andExpect(status().isOk());
    }

    @Test
    void authorApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/author")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
    }

    @Test
    @WithMockUser(username = "admin")
    void authorApiTestAuthorized() throws Exception {
        given(authorService.getAll()).willReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/author")).andExpect(status().isOk());
    }

    @Test
    void userApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/admin")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
    }

    @Test
    @WithMockUser(username = "admin")
    void userApiTestAuthorized() throws Exception {
        given(userService.findAll()).willReturn(List.of());
        given(userService.find("admin")).willReturn(new UserDto("admin", true));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/admin")).andExpect(status().isOk());
    }

    @Test
    void loginApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")).andExpect(status().isOk());
    }
}
