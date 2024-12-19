package com.example.talent_api.service;

import com.example.talent_api.domain.Candidate;
import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.repository.CandidateRepository;
import com.example.talent_api.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CandidateServiceImplTests {

    @InjectMocks
    private CandidateServiceImpl candidateService;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private Candidate candidate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User("JohnDoe", "password", UserType.CANDIDATE);
        candidate = new Candidate(user, "John Doe", "john.doe@example.com");
        candidate.setAddress("123 Main St");
        candidate.setPhone("123-456-7890");
        candidate.setResume("Sample resume");
        candidate.setFirstApplicationDate(LocalDateTime.now());
    }

    @Test
    void testGetCandidates() {
        when(candidateRepository.findAll()).thenReturn(Collections.singletonList(candidate));

        List<Candidate> response = candidateService.getCandidates();

        assertEquals(1, response.size());
        assertEquals("John Doe", response.getFirst().getFullName());
    }

    @Test
    void testGetCandidateById() {
        when(candidateRepository.findById(any(Long.class))).thenReturn(Optional.of(candidate));

        ResponseEntity<Candidate> response = candidateService.getCandidateById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("John Doe", Objects.requireNonNull(response.getBody()).getFullName());
    }

    @Test
    void testGetCandidateByIdNotFound() {
        when(candidateRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        ResponseEntity<Candidate> response = candidateService.getCandidateById(1L);

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

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(candidateRepository.save(any(Candidate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        JsonNode requestBody = mock(JsonNode.class);
        when(requestBody.get("user")).thenReturn(userNode);
        when(requestBody.get("candidate")).thenReturn(candidateNode);

        ResponseEntity<Candidate> response = candidateService.createCandidate(requestBody);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("John Doe", Objects.requireNonNull(response.getBody()).getFullName());
    }

    @Test
    void testCreateCandidateWithNonUniqueEmail() {
        JsonNode userNode = mock(JsonNode.class);
        when(userNode.get("id")).thenReturn(mock(JsonNode.class));
        when(userNode.get("id").asLong()).thenReturn(1L);

        List<String> existingEmails = new ArrayList<>();
        existingEmails.add("john.doe@example.com");

        JsonNode candidateNode = mock(JsonNode.class);
        when(candidateNode.get("fullName")).thenReturn(mock(JsonNode.class));
        when(candidateNode.get("email")).thenReturn(mock(JsonNode.class));
        when(candidateNode.get("address")).thenReturn(mock(JsonNode.class));
        when(candidateNode.get("phone")).thenReturn(mock(JsonNode.class));
        when(candidateNode.get("resume")).thenReturn(mock(JsonNode.class));

        when(candidateNode.get("fullName").asText()).thenReturn("Jane Doe");
        when(candidateNode.get("email").asText()).thenReturn("john.doe@example.com");
        when(candidateNode.get("address").asText()).thenReturn("456 Elm St");
        when(candidateNode.get("phone").asText()).thenReturn("987-654-3210");
        when(candidateNode.get("resume").asText()).thenReturn("Sample resume");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(candidateRepository.findAllEmails()).thenReturn(existingEmails);

        JsonNode requestBody = mock(JsonNode.class);
        when(requestBody.get("user")).thenReturn(userNode);
        when(requestBody.get("candidate")).thenReturn(candidateNode);

        ResponseEntity<Candidate> response = candidateService.createCandidate(requestBody);

        assertEquals(400, response.getStatusCode().value());
    }


    @Test
    void testCreateCandidateUserNotFound() {
        JsonNode userNode = mock(JsonNode.class);
        when(userNode.get("id")).thenReturn(mock(JsonNode.class));
        when(userNode.get("id").asLong()).thenReturn(1L);

        JsonNode candidateNode = mock(JsonNode.class);
        when(candidateNode.get("fullName")).thenReturn(mock(JsonNode.class));
        when(candidateNode.get("fullName").asText()).thenReturn("John Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        JsonNode requestBody = mock(JsonNode.class);
        when(requestBody.get("user")).thenReturn(userNode);
        when(requestBody.get("candidate")).thenReturn(candidateNode);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            candidateService.createCandidate(requestBody);
        });

        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdateCandidate() {
        when(candidateRepository.findById(any(Long.class))).thenReturn(Optional.of(candidate));

        Candidate updatedCandidate = new Candidate();
        updatedCandidate.setFullName("Johnny Doe");
        updatedCandidate.setEmail("johnny.doe@example.com");
        updatedCandidate.setAddress("456 Elm St");
        updatedCandidate.setPhone("987-654-3210");
        updatedCandidate.setResume("Updated resume");
        updatedCandidate.setFirstApplicationDate(LocalDateTime.now());

        ResponseEntity<Candidate> response = candidateService.updateCandidate(1L, updatedCandidate);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Johnny Doe", Objects.requireNonNull(response.getBody()).getFullName());
    }

    @Test
    void testUpdateCandidateWithSameEmail() {
        when(candidateRepository.findById(any(Long.class))).thenReturn(Optional.of(candidate));

        Candidate updatedCandidate = new Candidate();
        updatedCandidate.setFullName("Johnny Doe");
        updatedCandidate.setEmail("john.doe@example.com");
        updatedCandidate.setAddress("456 Elm St");
        updatedCandidate.setPhone("987-654-3210");
        updatedCandidate.setResume("Updated resume");

        ResponseEntity<Candidate> response = candidateService.updateCandidate(1L, updatedCandidate);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Johnny Doe", Objects.requireNonNull(response.getBody()).getFullName());
    }

    @Test
    void testUpdateCandidateNotFound() {
        when(candidateRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Candidate updatedCandidate = new Candidate();
        ResponseEntity<Candidate> response = candidateService.updateCandidate(1L, updatedCandidate);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testUpdateCandidateWithNonUniqueEmail() {
        List<String> existingEmails = new ArrayList<>();
        existingEmails.add("existing@example.com");

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(candidateRepository.findAllEmails()).thenReturn(existingEmails);

        Candidate updatedCandidate = new Candidate();
        updatedCandidate.setFullName("Johnny Doe");
        updatedCandidate.setEmail("existing@example.com");
        updatedCandidate.setAddress("456 Elm St");
        updatedCandidate.setPhone("987-654-3210");
        updatedCandidate.setResume("Updated resume");

        ResponseEntity<Candidate> response = candidateService.updateCandidate(1L, updatedCandidate);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testDeleteCandidate() {
        Long candidateId = 1L;

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<Void> response = candidateService.deleteCandidate(candidateId);

        assertEquals(204, response.getStatusCode().value());
        verify(userRepository, times(1)).save(user);
        verify(candidateRepository, times(1)).deleteById(candidateId);
    }

    @Test
    void testDeleteCandidateNotFound() {
        when(candidateRepository.existsById(any(Long.class))).thenReturn(false);

        ResponseEntity<Void> response = candidateService.deleteCandidate(1L);

        assertEquals(404, response.getStatusCode().value());
        verify(candidateRepository, never()).deleteById(1L);
    }
}