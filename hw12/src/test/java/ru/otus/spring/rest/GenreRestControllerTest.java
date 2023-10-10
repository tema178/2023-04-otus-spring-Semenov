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
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.restController.GenreRestController;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({GenreRestController.class, SecurityConfiguration.class})
@WithMockUser(username = "admin")
class GenreRestControllerTest {

    private static final Genre ACTION = new Genre(1, "Action");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldReturnGenreDtoAfterSaving() throws Exception {
        given(genreService.getAll()).willReturn(List.of(ACTION));
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/genre"));
        perform.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(GenreDto.toDto(ACTION)))));
    }
}
