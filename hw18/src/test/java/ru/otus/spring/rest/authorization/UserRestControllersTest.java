package ru.otus.spring.rest.authorization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.restController.UserRestController;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.UserService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SecurityConfiguration.class, UserRestController.class})
class UserRestControllersTest {

    public static final String LOGIN = "http://localhost/login";

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void userApiTestUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/admin")).andExpect(status().isFound())
                .andExpect(redirectedUrl(LOGIN));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void userApiTestAuthorizedAsAdmin() throws Exception {
        given(userService.findAll()).willReturn(List.of());
        given(userService.find("admin")).willReturn(new UserDto("admin", true));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/admin")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manager", roles = "MANAGER")
    void userApiTestAuthorizedAsManager() throws Exception {
        given(userService.findAll()).willReturn(List.of());
        given(userService.find("admin")).willReturn(new UserDto("admin", true));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")).andExpect(status().isForbidden());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/admin")).andExpect(status().isForbidden());
    }
}
