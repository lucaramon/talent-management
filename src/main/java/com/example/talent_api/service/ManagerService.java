package com.example.talent_api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.talent_api.domain.Manager;
import com.fasterxml.jackson.databind.JsonNode;

public interface ManagerService {
    
    List<Manager> getAllManagers();

    ResponseEntity<Manager> getManagerById(Long id);

    ResponseEntity<Manager> addManager(JsonNode requestBody);

    ResponseEntity<Manager> updateManager(Manager manager, Long id);

    ResponseEntity<?> deleteManager(Long id);
}
