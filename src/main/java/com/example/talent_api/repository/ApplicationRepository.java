package com.example.talent_api.repository;

import com.example.talent_api.domain.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
    @NonNull
    ArrayList<Application> findAll(); // Method to find all applications

    // Method to find applications by manager ID
    ArrayList<Application> findByJobManagerId(long managerId);

    // Method to find applications by job ID
    ArrayList<Application> findByJobId(long jobId);
}