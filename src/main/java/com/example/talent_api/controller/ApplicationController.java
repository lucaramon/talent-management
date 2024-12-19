package com.example.talent_api.controller;

import com.example.talent_api.domain.Application;
import com.example.talent_api.domain.Candidate;
import com.example.talent_api.domain.Job;
import com.example.talent_api.repository.ApplicationRepository;
import com.example.talent_api.repository.CandidateRepository;
import com.example.talent_api.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

@RestController
@RequestMapping("/applications") // Base URL for all application-related endpoints
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final CandidateRepository candidateRepository;

    @Autowired
    public ApplicationController(ApplicationRepository applicationRepository, JobRepository jobRepository, CandidateRepository candidateRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.candidateRepository = candidateRepository;
    }

    @GetMapping
    public ArrayList<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @GetMapping("/manager/{manager_id}")
    public ResponseEntity<ArrayList<Application>> getApplicationsByManagerId(@PathVariable long manager_id) {
        ArrayList<Application> applications = applicationRepository.findByJobManagerId(manager_id);
        return applications.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(applications);
    }

    @GetMapping("/job/{job_id}")
    public ResponseEntity<ArrayList<Application>> getApplicationsByJobId(@PathVariable long job_id) {
        ArrayList<Application> applications = applicationRepository.findByJobId(job_id);
        return applications.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable long id) {
        return applicationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Job job = application.getJob();
        if (job == null || job.getId() == 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Candidate candidate = application.getCandidate();
        if (candidate == null || candidate.getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        final Long jobId = job.getId();
        final Long candidateId = candidate.getId();

        Job foundJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

        Candidate foundCandidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + candidateId));

        application.setJob(foundJob);
        application.setCandidate(foundCandidate);

        if (application.getDateApplied() == null) {
            application.setDateApplied(Timestamp.from(Instant.now()));
        }

        Application savedApplication = applicationRepository.save(application);

        return ResponseEntity.status(201).body(savedApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable long id, @RequestBody Application application) {
        if (!applicationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Job job = application.getJob();
        if (job == null || job.getId() == 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Candidate candidate = application.getCandidate();
        if (candidate == null || candidate.getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        final Long jobId = job.getId();
        final Long candidateId = candidate.getId();

        Job foundJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

        Candidate foundCandidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + candidateId));

        application.setJob(foundJob);
        application.setCandidate(foundCandidate);
        application.setId(id);

        Application updatedApplication = applicationRepository.save(application);

        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicationById(@PathVariable long id) {
        if (applicationRepository.existsById(id)) {
            applicationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
