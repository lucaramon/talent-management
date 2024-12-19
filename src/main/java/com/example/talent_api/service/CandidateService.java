package com.example.talent_api.service;

import com.example.talent_api.domain.Candidate;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CandidateService {

    List<Candidate> getCandidates();

    ResponseEntity<Candidate> getCandidateById(Long id);

    ResponseEntity<Candidate> createCandidate(JsonNode requestBody);

    ResponseEntity<Candidate> updateCandidate(Long id, Candidate updatedCandidate);

    ResponseEntity<Void> deleteCandidate(Long id);
}
