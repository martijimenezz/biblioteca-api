package com.biblioteca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Entity Tests")
class UserTest {

    private User user;

    private User createUser(String name, String email, String phone) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        return user;
    }

    @Test
    @DisplayName("Should create User with all fields")
    void testUserConstructor() {
        user = new User(1L, "John Doe", "john@example.com", "+34-987654321");
        
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("+34-987654321", user.getPhone());
    }

    @Test
    @DisplayName("Should create User with default constructor")
    void testUserDefaultConstructor() {
        user = new User();
        
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getPhone());
    }

    @Test
    @DisplayName("Should set and get user name")
    void testSetAndGetName() {
        user = createUser("Jane Smith", "jane@example.com", "+34-666555444");
        
        assertEquals("Jane Smith", user.getName());
        
        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName());
    }

    @Test
    @DisplayName("Should set and get user email")
    void testSetAndGetEmail() {
        user = createUser("Alice", "alice@example.com", "+34-111222333");
        
        assertEquals("alice@example.com", user.getEmail());
        
        user.setEmail("alice.new@example.com");
        assertEquals("alice.new@example.com", user.getEmail());
    }

    @Test
    @DisplayName("Should set and get user phone")
    void testSetAndGetPhone() {
        user = createUser("Bob", "bob@example.com", "+34-444555666");
        
        assertEquals("+34-444555666", user.getPhone());
        
        user.setPhone("+34-999888777");
        assertEquals("+34-999888777", user.getPhone());
    }

    @Test
    @DisplayName("Should validate user with different phone formats")
    void testDifferentPhoneFormats() {
        user = createUser("User1", "user1@example.com", "999-888-7777");
        assertEquals("999-888-7777", user.getPhone());
        
        user = createUser("User2", "user2@example.com", "(555) 123-4567");
        assertEquals("(555) 123-4567", user.getPhone());
    }

    @Test
    @DisplayName("Should handle optional phone field")
    void testOptionalPhone() {
        user = createUser("User", "user@example.com", null);
        
        assertNull(user.getPhone());
        
        user.setPhone("+34-123456789");
        assertEquals("+34-123456789", user.getPhone());
    }
}
