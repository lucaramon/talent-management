package com.example.talent_api.controller;

import com.example.talent_api.domain.Candidate;
import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.service.CandidateServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CandidateControllerTests {

    @InjectMocks
    private CandidateController candidateController;

    @Mock
    private CandidateServiceImpl candidateService;

    private Candidate candidate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user = new User("JohnDoe", "password", UserType.CANDIDATE);
        candidate = new Candidate(user, "John Doe", "john.doe@example.com");
        candidate.setAddress("123 Main St");
        candidate.setPhone("123-456-7890");
        candidate.setResume("Sample resume");
        candidate.setFirstApplicationDate(LocalDateTime.now());
    }

    @Test
    void testGetCandidates() {
        when(candidateService.getCandidates()).thenReturn(Collections.singletonList(candidate));

        List<Candidate> response = candidateController.getCandidates();

        assertEquals(1, response.size());
        assertEquals("John Doe", response.getFirst().getFullName());
    }

    @Test
    void testGetCandidatesIsEmpty() {
        when(candidateService.getCandidates()).thenReturn(new ArrayList<>());

        List<Candidate> response = candidateController.getCandidates();

        assertEquals(0, response.size());
    }

    @Test
    void testGetCandidateById() {
        when(candidateService.getCandidateById(any(Long.class))).thenReturn(ResponseEntity.ok(candidate));

        ResponseEntity<Candidate> response = candidateController.getCandidateById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("John Doe", Objects.requireNonNull(response.getBody()).getFullName());
    }

    @Test
    void testGetCandidateByIdNotFound() {
        when(candidateService.getCandidateById(any(Long.class))).thenReturn(ResponseEntity.notFound().build());

        ResponseEntity<Candidate> response = candidateController.getCandidateById(1L);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testCreateCandidate() {
        JsonNode userNode = mock(JsonNode.class);
        when(userNode.get("id")).thenReturn(mock(JsonNode.class));
        when(userNode.get("id").asLong()).thenReturn(1L);

        JsonNode candidateNode = mock(JsonNode.class);
        when(candidateNode.get("fullName")).thenReturn(mock(JsonNode.class));
        when(candidateNode.get("email")).thenReturn(mock(JsonNode.class));
        when(candidateNode.get("address")).thenReturn(mock(JsonNode.class));
        when(candidateNode.get("phone")).thenReturn(mock(JsonNode.class));
        when(candidateNode.get("resume")).thenReturn(mock(JsonNode.class));

        when(candidateNode.get("fullName").asText()).thenReturn("John Doe");
        when(candidateNode.get("email").asText()).thenReturn("john.doe@example.com");
        when(candidateNode.get("address").asText()).thenReturn("123 Main St");
        when(candidateNode.get("phone").asText()).thenReturn("123-456-7890");
        when(candidateNode.get("resume").asText()).thenReturn("Sample resume");

        when(candidateService.createCandidate(any(JsonNode.class))).thenReturn(ResponseEntity.status(201).body(candidate));

        JsonNode requestBody = mock(JsonNode.class);
        when(requestBody.get("user")).thenReturn(userNode);
        when(requestBody.get("candidate")).thenReturn(candidateNode);

        ResponseEntity<Candidate> response = candidateController.createCandidate(requestBody);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("John Doe", Objects.requireNonNull(response.getBody()).getFullName());
    }

    @Test
    void testUpdateCandidate() {
        Candidate updatedCandidate = new Candidate();
        updatedCandidate.setFullName("Johnny Doe");
        updatedCandidate.setEmail("johnny.doe@example.com");
        updatedCandidate.setAddress("456 Elm St");
        updatedCandidate.setPhone("987-654-3210");
        updatedCandidate.setResume("Updated resume");
        updatedCandidate.setFirstApplicationDate(LocalDateTime.now());

        when(candidateService.updateCandidate(any(Long.class), any(Candidate.class)))
                .thenReturn(ResponseEntity.ok(updatedCandidate));

        ResponseEntity<Candidate> response = candidateController.updateCandidate(1L, updatedCandidate);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Johnny Doe", Objects.requireNonNull(response.getBody()).getFullName());
    }

    @Test
    void testUpdateCandidateNotFound() {
        when(candidateService.updateCandidate(any(Long.class), any(Candidate.class)))
                .thenReturn(ResponseEntity.notFound().build());

        ResponseEntity<Candidate> response = candidateController.updateCandidate(1L, new Candidate());

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testDeleteCandidate() {
        when(candidateService.deleteCandidate(any(Long.class))).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = candidateController.deleteCandidate(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(candidateService, times(1)).deleteCandidate(1L);
    }

    @Test
    void testDeleteCandidateNotFound() {
        when(candidateService.deleteCandidate(any(Long.class))).thenReturn(ResponseEntity.notFound().build());

        ResponseEntity<Void> response = candidateController.deleteCandidate(1L);

        assertEquals(404, response.getStatusCode().value());
    }
}