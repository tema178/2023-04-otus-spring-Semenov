package ru.otus.spring.rest.authorization;

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
import ru.otus.spring.restController.BookRestController;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SecurityConfiguration.class,
        BookRestController.class})
public class BookRestControllerTest {

    public static final String LOGIN = "http://localhost/login";

    @MockBean
    private BookService bookService;

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

}
