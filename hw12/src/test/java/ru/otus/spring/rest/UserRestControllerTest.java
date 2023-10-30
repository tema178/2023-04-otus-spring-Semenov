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
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.restController.UserRestController;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.UserService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserRestController.class, SecurityConfiguration.class})
@WithMockUser(username = "admin")
class UserRestControllerTest {

    public static final UserDto ADMIN = new UserDto("admin", true);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<UserDto> userDtoList = List.of(
            new UserDto("user", true),
            ADMIN
    );

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldReturnUserDtoList() throws Exception {
        given(userService.findAll()).willReturn(userDtoList);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/user"));
        perform.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(userDtoList)));
    }

    @Test
    void shouldReturnUserByName() throws Exception {
        given(userService.find("admin")).willReturn(ADMIN);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/admin"));
        perform.andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(ADMIN)));
    }
}
