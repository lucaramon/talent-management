package com.example.talent_api.controller;

import com.example.talent_api.domain.Candidate;
import com.example.talent_api.service.CandidateServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateServiceImpl candidateService;

    @GetMapping
    public List<Candidate> getCandidates() {
        return candidateService.getCandidates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        return candidateService.getCandidateById(id);
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody JsonNode requestBody) {
        return candidateService.createCandidate(requestBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @RequestBody Candidate updatedCandidate) {
        return candidateService.updateCandidate(id, updatedCandidate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        return candidateService.deleteCandidate(id);
    }
}