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
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.restController.CommentsRestController;
import ru.otus.spring.service.CommentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentsRestController.class)
class CommentRestControllerTest {

    public static final Comment COMMENT = new Comment(1, new Book(1, "book", null, null), "comment");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldReturnCommentById() throws Exception {
        given(commentService.findById(1)).willReturn(COMMENT);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/1"));
        perform.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(CommentDto.toDto(COMMENT))));
    }

    @Test
    void shouldHaveDeleteEndpoint() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.delete("/api/comment/1"));
        perform.andExpect(status().isOk());
    }

    @Test
    void shouldReturnCommentDtoAfterSaving() throws Exception {
        given(commentService.save(any())).willReturn(COMMENT);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.put("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(CommentDto.toDto(COMMENT))));
        perform.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(CommentDto.toDto(COMMENT))));
    }
}
