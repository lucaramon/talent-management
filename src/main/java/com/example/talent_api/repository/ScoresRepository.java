package com.example.talent_api.repository;

import com.example.talent_api.domain.Scores;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Optional;

public interface ScoresRepository extends CrudRepository<Scores, Long> {
    @Override
    @NonNull
    ArrayList<Scores> findAll();

    Optional<Scores> findByApplication_Id(Long applicationId);
}
