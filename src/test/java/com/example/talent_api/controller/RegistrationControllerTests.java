package com.example.talent_api.controller;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.repository.UserRepository;
import com.example.talent_api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void create_ValidUser_ReturnsUser() throws Exception {
        // Arrange
        User user = new User("testUser", "testPassword", UserType.USER);
        when(userRepository.save(isA(User.class))).thenReturn(
                user
        );
        // Act & Assert
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void create_InvalidUser_ReturnsUnauthorized() throws Exception {
        // Arrange
        User user = new User(null, null, UserType.USER); // Invalid user

        // Act & Assert
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void health_ReturnsStatusOk() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(content().string("Registration endpoint"));
    }
}
