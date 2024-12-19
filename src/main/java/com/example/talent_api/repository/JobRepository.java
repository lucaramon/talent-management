package com.example.talent_api.repository;

import com.example.talent_api.domain.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public interface JobRepository extends CrudRepository<Job, Long> {
        @NonNull
        ArrayList<Job> findAll();

        ArrayList<Job> findByManagerId(long id);

}
