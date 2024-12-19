package com.example.talent_api.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTests {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "testPass", UserType.MANAGER);
    }

    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertEquals("testPass", user.getPassword());
        assertEquals(UserType.MANAGER, user.getType());
        assertEquals("manager", user.getTypeString());
    }

    @Test
    void testSetters() {
        user.setUsername("updatedUser");
        assertEquals("updatedUser", user.getUsername());
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
        user.setType(UserType.USER);
        assertEquals(UserType.USER, user.getType());
        assertEquals("user", user.getTypeString());
    }
}
