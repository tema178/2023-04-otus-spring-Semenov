package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.restController.AuthorRestController;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.AuthorService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AuthorRestController.class, SecurityConfiguration.class})
@WithMockUser(username = "admin")
class AuthorRestControllerTest {

    private static final Author IVAN = new Author(1, "Ivan");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldReturnAuthorDtoAfterSaving() throws Exception {
        given(authorService.getAll()).willReturn(List.of(IVAN));
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/author"));
        perform.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(AuthorDto.toDto(IVAN)))));
    }
}
