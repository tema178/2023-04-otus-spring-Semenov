package ru.otus.spring.rest.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.SimpleAuthority;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.restController.GenreRestController;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SecurityConfiguration.class, GenreRestController.class})
public class GenreRestControllerTest {


    public static final String LOGIN = "http://localhost/login";

    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void genreApiTestUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/genre")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/genre").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/genre/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
    }

    private static Stream<SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor> getUsers() {
        return Stream.of(user("user"), user("admin").authorities(
                List.of(new SimpleAuthority("ADMIN"))));
    }

    @ParameterizedTest
    @MethodSource("getUsers")
    void genreApiTestAuthorized(SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor user) throws Exception {
        given(genreService.getAll()).willReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/genre").with(user)).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/genre").with(user).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/genre/1").with(user).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "MANAGER")
    void genreApiTestAuthorizedAsManager() throws Exception {
        given(genreService.getAll()).willReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/genre")).andExpect(status().isOk());
        given(genreService.save(any())).willReturn(new Genre());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/genre").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(GenreDto.toDto(new Genre()))))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/genre/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
