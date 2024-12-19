package com.example.talent_api.service;

import com.example.talent_api.domain.Candidate;
import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.repository.CandidateRepository;
import com.example.talent_api.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository, UserRepository userRepository) {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Candidate> getCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public ResponseEntity<Candidate> getCandidateById(Long id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        return candidate.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Candidate> createCandidate(JsonNode requestBody) {
        JsonNode userNode = requestBody.get("user");
        Long userId = userNode.get("id").asLong();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setType(UserType.CANDIDATE);
        userRepository.save(user);

        JsonNode candidateNode = requestBody.get("candidate");
        Candidate newCandidate = new Candidate();
        newCandidate.setUser(user);

        String email = candidateNode.get("email").asText();
        if (emailAlreadyUsed(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        newCandidate.setEmail(email);

        newCandidate.setFullName(candidateNode.get("fullName").asText());
        newCandidate.setAddress(candidateNode.get("address").asText());
        newCandidate.setPhone(candidateNode.get("phone").asText());
        newCandidate.setResume(candidateNode.get("resume").asText());
        newCandidate.setFirstApplicationDate(LocalDateTime.now());
        newCandidate.setApplications(new ArrayList<>());

        Candidate savedCandidate = candidateRepository.save(newCandidate);

        return ResponseEntity.status(201).body(savedCandidate);
    }

    @Override
    public ResponseEntity<Candidate> updateCandidate(Long id, Candidate updatedCandidate) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(id);

        if (candidateOptional.isPresent()) {
            Candidate candidate = candidateOptional.get();

            String newEmail = updatedCandidate.getEmail();
            String currentEmail = candidate.getEmail();

            if (!newEmail.equals(currentEmail) && emailAlreadyUsed(newEmail)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            candidate.setEmail(newEmail);
            candidate.setFullName(updatedCandidate.getFullName());
            candidate.setAddress(updatedCandidate.getAddress());
            candidate.setPhone(updatedCandidate.getPhone());
            candidate.setResume(updatedCandidate.getResume());
            candidate.setFirstApplicationDate(updatedCandidate.getFirstApplicationDate());

            candidateRepository.save(candidate);

            return ResponseEntity.ok(candidate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteCandidate(Long id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);

        if (candidate.isPresent()) {

            Long userId = candidate.get().getUser().getId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            user.setType(UserType.USER);
            userRepository.save(user);

            candidateRepository.deleteById(id);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean emailAlreadyUsed(String email) {
        List<String> allCandidateEmails = candidateRepository.findAllEmails();
        return allCandidateEmails.contains(email);
    }
}