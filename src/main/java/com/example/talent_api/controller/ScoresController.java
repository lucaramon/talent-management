package com.example.talent_api.controller;

import com.example.talent_api.domain.Scores;
import com.example.talent_api.service.ScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicationScores")
public class ScoresController {

    private final ScoresService scoresService;

    @Autowired
    public ScoresController(ScoresService scoresService) {
        this.scoresService = scoresService;
    }

    @GetMapping
    public List<Scores> getScores() { return scoresService.getAllScores(); }

    @GetMapping("/{id}")
    public ResponseEntity<Scores> getScoresById(@PathVariable Long id) {
        return scoresService.getScoresById(id);
    }

    @GetMapping(value = "/byApplicationId")
    public ResponseEntity<Scores> getScoresByApplicationId(@RequestParam Long applicationId) {
        return scoresService.getScoresByApplicationId(applicationId);
    }
}
