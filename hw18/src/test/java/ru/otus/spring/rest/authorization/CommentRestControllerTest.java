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
import ru.otus.spring.domain.Comment;
import ru.otus.spring.restController.CommentsRestController;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.CommentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SecurityConfiguration.class, CommentsRestController.class})
public class CommentRestControllerTest {

    public static final String LOGIN = "http://localhost/login";

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

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
}
