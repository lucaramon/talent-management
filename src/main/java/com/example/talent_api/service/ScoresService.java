package com.example.talent_api.service;

import com.example.talent_api.domain.Application;
import com.example.talent_api.domain.Scores;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ScoresService {
    List<Scores> getAllScores();
    ResponseEntity<Scores> getScoresById(Long id);
    ResponseEntity<Scores> getScoresByApplicationId(Long id);
    Optional<Scores> createScores(Application application);
}
