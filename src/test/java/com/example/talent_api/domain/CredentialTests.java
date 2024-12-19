package com.example.talent_api.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CredentialTests {

    @Test
    void testDefaultConstructor() {
        Credentials credentials = new Credentials();
        assertEquals(null, credentials.getUsername());
        assertEquals(null, credentials.getPassword());
    }

    @Test
    void testParameterizedConstructor() {
        Credentials credentials = new Credentials("testUser", "testPassword");
        assertEquals("testUser", credentials.getUsername());
        assertEquals("testPassword", credentials.getPassword());
    }

    @Test
    void testSetUsername() {
        Credentials credentials = new Credentials();
        credentials.setUsername("newUser");
        assertEquals("newUser", credentials.getUsername());
    }

    @Test
    void testSetPassword() {
        Credentials credentials = new Credentials();
        credentials.setPassword("newPassword");
        assertEquals("newPassword", credentials.getPassword());
    }
}
