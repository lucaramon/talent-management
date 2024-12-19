package com.example.talent_api.service;

import com.example.talent_api.domain.Application;
import com.example.talent_api.domain.Scores;
import com.example.talent_api.repository.ScoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoresServiceImpl implements ScoresService {

    private final ScoresRepository scoresRepository;

    @Autowired
    public ScoresServiceImpl(ScoresRepository scoresRepository) {
        this.scoresRepository = scoresRepository;
    }

    @Override
    public List<Scores> getAllScores() {
        return scoresRepository.findAll();
    }

    @Override
    public ResponseEntity<Scores> getScoresById(Long id) {
        Optional<Scores> scores = scoresRepository.findById(id);
        return scores.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Scores> getScoresByApplicationId(Long applicationId) {
        Optional<Scores> scores = scoresRepository.findByApplication_Id(applicationId);
        return scores.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public Optional<Scores> createScores(Application application) {
        // For future implementation
        return Optional.empty();
    }
}
