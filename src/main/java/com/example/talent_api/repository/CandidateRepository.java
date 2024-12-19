package com.example.talent_api.repository;

import com.example.talent_api.domain.Candidate;
import com.example.talent_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("SELECT c.email FROM Candidate c")
    List<String> findAllEmails();

    Optional <Candidate> findCandidateByUser(User user);
}
