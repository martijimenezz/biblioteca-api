package com.biblioteca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Entity Tests")
class UserTest {

    private User user;

    private User createUser(String name, String email, String memberId) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setMemberId(memberId);
        user.setActive(true);
        return user;
    }

    @Test
    @DisplayName("Should create User with all fields")
    void testUserConstructor() {
        user = new User(1L, "John Doe", "john@example.com", "MEMBER001", "Madrid", true);
        
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("MEMBER001", user.getMemberId());
        assertEquals("Madrid", user.getCity());
        assertTrue(user.getActive());
    }

    @Test
    @DisplayName("Should create User with default constructor")
    void testUserDefaultConstructor() {
        user = new User();
        
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getMemberId());
    }

    @Test
    @DisplayName("Should set and get user name")
    void testSetAndGetName() {
        user = createUser("Jane Smith", "jane@example.com", "MEMBER002");
        
        assertEquals("Jane Smith", user.getName());
        
        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName());
    }

    @Test
    @DisplayName("Should set and get user email")
    void testSetAndGetEmail() {
        user = createUser("Alice", "alice@example.com", "MEMBER003");
        
        assertEquals("alice@example.com", user.getEmail());
        
        user.setEmail("alice.new@example.com");
        assertEquals("alice.new@example.com", user.getEmail());
    }

    @Test
    @DisplayName("Should set and get member ID")
    void testSetAndGetMemberId() {
        user = createUser("Bob", "bob@example.com", "MEMBER004");
        
        assertEquals("MEMBER004", user.getMemberId());
        
        user.setMemberId("MEMBER005");
        assertEquals("MEMBER005", user.getMemberId());
    }

    @Test
    @DisplayName("Should set and get user city")
    void testSetAndGetCity() {
        user = createUser("User", "user@example.com", "MEMBER006");
        
        assertNull(user.getCity());
        
        user.setCity("Barcelona");
        assertEquals("Barcelona", user.getCity());
    }

    @Test
    @DisplayName("Should manage user active status")
    void testActiveStatus() {
        user = createUser("User", "user@example.com", "MEMBER007");
        
        assertTrue(user.getActive());
        
        user.setActive(false);
        assertFalse(user.getActive());
    }
}
