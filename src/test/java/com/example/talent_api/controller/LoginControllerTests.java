package com.example.talent_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.talent_api.domain.Credentials;
import com.example.talent_api.domain.LoginResponse;
import com.example.talent_api.domain.Manager;
import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.repository.CandidateRepository;
import com.example.talent_api.repository.ManagerRepository;
import com.example.talent_api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LoginController.class)
public class LoginControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ManagerRepository managerRepository;

    @MockBean
    private CandidateRepository candidateRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void create_ValidCredentials_ReturnsLoginResponse() throws Exception {
        // Arrange
        Credentials credentials = new Credentials("testUser", "testPassword");
        User user = new User("testUser", "testPassword", UserType.MANAGER);
        Manager manager = new Manager(user, "John Doe", "john.doe@example.com", "Sales", "1234567890");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(managerRepository.findManagerByUser(user)).thenReturn(Optional.of(manager));

        LoginResponse expectedResponse = new LoginResponse(user, manager);

        // Act & Assert
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void create_InvalidUsername_ReturnsUnauthorized() throws Exception {
        // Arrange
        Credentials credentials = new Credentials("wrongUser", "testPassword");
        when(userRepository.findByUsername("wrongUser")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void create_InvalidPassword_ReturnsUnauthorized() throws Exception {
        // Arrange
        Credentials credentials = new Credentials("testUser", "wrongPassword");
        User user = new User("testUser", "testPassword", UserType.CANDIDATE);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Act & Assert
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void health_ReturnsStatusOk() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login endpoint"));
    }
}
