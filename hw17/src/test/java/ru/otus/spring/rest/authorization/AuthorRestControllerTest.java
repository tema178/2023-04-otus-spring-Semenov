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
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.SimpleAuthority;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.restController.AuthorRestController;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.AuthorService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SecurityConfiguration.class, AuthorRestController.class})
public class AuthorRestControllerTest {

    public static final String LOGIN = "http://localhost/login";

    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void authorApiTestUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/author")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/author").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/author/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
    }

    @Test
    void authorApiTestAuthorized() throws Exception {
        given(authorService.getAll()).willReturn(List.of());
        var users = List.of(user("user"), user("admin").authorities(
                List.of(new SimpleAuthority("ADMIN"))));
        for (var user : users) {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/author").with(user)).andExpect(status().isOk());
            mockMvc.perform(MockMvcRequestBuilders.put("/api/author").with(user).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/author/1").with(user).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = "MANAGER")
    void authorApiTestAuthorizedAsManager() throws Exception {
        given(authorService.getAll()).willReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/author")).andExpect(status().isOk());
        given(authorService.save(any())).willReturn(new Author());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/author").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(AuthorDto.toDto(new Author()))))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/author/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
