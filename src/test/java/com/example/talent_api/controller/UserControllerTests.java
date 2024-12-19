package com.example.talent_api.controller;

import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldReturnAllUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(
                new ArrayList<>(Arrays.asList(
                        new User("testuser", "rosebud", UserType.USER),
                        new User("usertest", "budrose", UserType.USER),
                        new User("bossman", "topdog", UserType.MANAGER)
                ))
        );
        this.mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[1].password").value("budrose"))
                .andExpect(jsonPath("$[2].type").value(UserType.MANAGER.toString()));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        when(userRepository.findById(isA(Long.class))).thenReturn(
                Optional.of(new User("testuser", "password", UserType.MANAGER)));
        this.mockMvc.perform(get("/users/1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(jsonPath("$.type").value(UserType.MANAGER.toString()));
    }

    @Test
    void shouldReturnCreatedUser() throws Exception {
        when(userRepository.save(isA(User.class))).thenReturn(
                new User("thetoppestdog", "incontrol", UserType.ADMIN)
        );
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"thetoppestdog\", " +
                                "\"password\":\"incontrol\", " +
                                "\"type\":\"ADMIN\"}"
                        )).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("thetoppestdog"))
                .andExpect(jsonPath("$.password").value("incontrol"))
                .andExpect(jsonPath("$.type").value(UserType.ADMIN.toString()));
    }

    @Test
    void shouldFailToCreateUserWithExistingUsername() throws Exception {
        when(userRepository.findByUsername(isA(String.class))).thenReturn(
                Optional.of(new User("userTest", "wordPass", UserType.USER))
        );
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"userTest\", \"password\":\"newPass\", \"type\":\"ADMIN\"}"
                        )).andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        when(userRepository.findById(isA(Long.class))).thenReturn(
                Optional.of(
                        new User("testUser", "password", UserType.ADMIN)
                )
        );
        User updateRes = new User("testUser", "updatedPass", UserType.USER);
        when(userRepository.save(isA(User.class))).thenReturn(updateRes);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\", " +
                        "\"password\":\"updatedPass\", " +
                        "\"type\":\"USER\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.password").value("updatedPass"))
                .andExpect(jsonPath("$.type").value(UserType.USER.toString()));
    }

    @Test
    void shouldFailToUpdateNonExistingUser() throws Exception {
        when(userRepository.findById(isA(Long.class))).thenReturn(Optional.empty());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\", " +
                        "\"password\":\"updatedPass\", " +
                        "\"type\":\"MANAGER\"}"
                )
        ).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnOkOnDelete() throws Exception {
        when(userRepository.findById(isA(Long.class))).thenReturn(
                Optional.of(new User("deleteUser", "deletePass", UserType.ADMIN))
        );
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(status().isOk());
    }
}
