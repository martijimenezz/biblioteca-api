package com.biblioteca.controller;

import com.biblioteca.model.User;
import com.biblioteca.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@DisplayName("UserController Integration Tests")
@Transactional
class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setMemberId("M001");
        user.setCity("Barcelona");
        user.setActive(true);
        user = userRepository.save(user);
    }

    @Test
    @DisplayName("Should GET all users")
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", equalTo("John Doe")));
    }

    @Test
    @DisplayName("Should GET user by ID")
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("john@example.com")))
                .andExpect(jsonPath("$.memberId", equalTo("M001")));
    }

    @Test
    @DisplayName("Should POST create new user")
    void testCreateUser() throws Exception {
        User newUser = new User();
        newUser.setName("Jane Doe");
        newUser.setEmail("jane@example.com");
        newUser.setMemberId("M002");
        newUser.setCity("Madrid");
        newUser.setActive(true);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Jane Doe")))
                .andExpect(jsonPath("$.email", equalTo("jane@example.com")));
    }

    @Test
    @DisplayName("Should PUT update user")
    void testUpdateUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setName("John Smith");
        updatedUser.setEmail("john@example.com");
        updatedUser.setMemberId("M001");
        updatedUser.setCity("Valencia");
        updatedUser.setActive(true);

        mockMvc.perform(put("/api/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("John Smith")));
    }

    @Test
    @DisplayName("Should DELETE user")
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 404 for non-existent user")
    void testGetNonExistentUser() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }
}
