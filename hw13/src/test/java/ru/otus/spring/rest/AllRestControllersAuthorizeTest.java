package ru.otus.spring.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({
        BookRestControllerTest.class
})
class AllRestControllersAuthorizeTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void bookApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book")).andExpect(status().isUnauthorized());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/1")).andExpect(status().isUnauthorized());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/1").with(csrf())).andExpect(status().isUnauthorized());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/book").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("some book"));
    }

    @Test
    void commentApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/1")).andExpect(status().isUnauthorized());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/comment/1")
                .with(csrf())).andExpect(status().isUnauthorized());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/comment").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("some comment"));
    }

    @Test
    void genreApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/genre")).andExpect(status().isUnauthorized());
    }

    @Test
    void authorApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/author")).andExpect(status().isUnauthorized());
    }

    @Test
    void userApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")).andExpect(status().isUnauthorized());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/admin")).andExpect(status().isUnauthorized());
    }

    @Test
    void loginApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")).andExpect(status().isOk());
    }
}
