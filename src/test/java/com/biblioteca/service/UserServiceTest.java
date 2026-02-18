package com.biblioteca.service;

import com.biblioteca.model.User;
import com.biblioteca.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("UserService Unit Tests")
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setMemberId("M001");
        user.setCity("Barcelona");
        user.setActive(true);
    }

    @Test
    @DisplayName("Should retrieve all users")
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve user by ID")
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    @DisplayName("Should create a new user")
    void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);
        User created = userService.createUser(user);
        assertNotNull(created);
        assertEquals("john@example.com", created.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should update an existing user")
    void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setName("Jane Doe");
        updatedUser.setEmail("jane@example.com");
        updatedUser.setMemberId("M001");
        updatedUser.setCity("Madrid");
        updatedUser.setActive(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(1L, updatedUser);
        assertNotNull(result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should delete a user")
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void testUpdateUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.updateUser(999L, user));
    }
}
