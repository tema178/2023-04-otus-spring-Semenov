package ru.otus.spring.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.spring.security.SecurityConfiguration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecurityConfiguration.class)
class AllRestControllersAuthorizeTest {

    public static final String LOGIN = "http://localhost/login";

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
    void genreApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/genre")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
    }

    @Test
    void authorApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/author")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
    }

    @Test
    void userApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/admin")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
    }

    @Test
    void loginApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")).andExpect(status().isOk());
    }
}
