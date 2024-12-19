package com.example.talent_api.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CandidateTests {

    private Candidate candidate;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        candidate = new Candidate(user, "John Doe", "john.doe@example.com");
    }

    @Test
    void testCandidateCreation() {
        assertNotNull(candidate);
        assertEquals(user, candidate.getUser());
        assertEquals("John Doe", candidate.getFullName());
        assertEquals("john.doe@example.com", candidate.getEmail());
        assertNull(candidate.getAddress());
        assertNull(candidate.getPhone());
        assertNull(candidate.getResume());
        assertNull(candidate.getFirstApplicationDate());
        assertTrue(candidate.getApplications().isEmpty());
    }

    @Test
    void testSettersAndGetters() {

        User user = new User("user", "password", UserType.CANDIDATE);

        candidate.setUser(user);
        candidate.setFullName("Johnny Doe");
        candidate.setEmail("johnny.doe@example.com");
        candidate.setAddress("123 Main St");
        candidate.setPhone("555-1234");
        candidate.setResume("Experienced software developer");
        LocalDateTime applicationDate = LocalDateTime.now();
        candidate.setFirstApplicationDate(applicationDate);
        candidate.setApplications(new ArrayList<>());

        assertEquals(user, candidate.getUser());
        assertEquals("Johnny Doe", candidate.getFullName());
        assertEquals("johnny.doe@example.com", candidate.getEmail());
        assertEquals("123 Main St", candidate.getAddress());
        assertEquals("555-1234", candidate.getPhone());
        assertEquals("Experienced software developer", candidate.getResume());
        assertEquals(applicationDate, candidate.getFirstApplicationDate());
        assertEquals(new ArrayList<>(), candidate.getApplications());
    }
}