package com.example.talent_api.controller;

import com.example.talent_api.domain.Application;
import com.example.talent_api.domain.Candidate;
import com.example.talent_api.domain.Job;
import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.repository.ApplicationRepository;
import com.example.talent_api.repository.CandidateRepository;
import com.example.talent_api.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ApplicationControllerTests {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private ApplicationController applicationController;

    private Application application;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create User object
        User user = new User("john_doe", "password", UserType.CANDIDATE); // Using the constructor

        // Create Candidate object with the User
        Candidate candidate = new Candidate(user, "John Doe", "john.doe@example.com");

        // Create Job object
        Job job = new Job();
        job.setId(1L); // Assuming you have a setId method in the Job class

        // Set up the application
        application = new Application();
        application.setId(1L);
        application.setJob(job);
        application.setCandidate(candidate);
        application.setDateApplied(Timestamp.from(Instant.now()));
    }

    @Test
    void testGetAllApplications() {
        ArrayList<Application> applications = new ArrayList<>();
        applications.add(application);
        when(applicationRepository.findAll()).thenReturn(applications);

        ArrayList<Application> result = applicationController.getAllApplications();

        assertEquals(1, result.size());
        verify(applicationRepository, times(1)).findAll();
    }

    @Test
    void testGetApplicationById() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        ResponseEntity<Application> response = applicationController.getApplicationById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(application, response.getBody());
    }

    @Test
    void testDeleteApplicationById() {
        when(applicationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(applicationRepository).deleteById(1L);

        ResponseEntity<Void> response = applicationController.deleteApplicationById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(applicationRepository, times(1)).deleteById(1L);
    }
}

