package com.example.talent_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.talent_api.domain.Manager;
import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.repository.ManagerRepository;
import com.example.talent_api.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository, UserRepository userRepository) {
        this.managerRepository = managerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public ResponseEntity<Manager> getManagerById(Long id) {
        Optional<Manager> manager = managerRepository.findById(id);
        return manager.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Manager> addManager(JsonNode requestBody) {
        JsonNode userNode = requestBody.get("user");
        if (userNode == null || !userNode.has("id")) {
            return ResponseEntity.badRequest().body(null);
        }

        Long userId = userNode.get("id").asLong();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId)); 

        user.setType(UserType.MANAGER);
        userRepository.save(user);

        JsonNode managerNode = requestBody.get("manager");
        if (managerNode == null) {
            return ResponseEntity.badRequest().body(null); 
        }

        Manager newManager = new Manager();
        newManager.setUser(user);
        newManager.setFullName(managerNode.get("fullName").asText());
        newManager.setEmail(managerNode.get("email").asText());
        newManager.setDepartment(managerNode.get("department").asText());
        newManager.setPhone(managerNode.get("phone").asText());

        Manager savedManager = managerRepository.save(newManager);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedManager);
    }

    @Override
    public ResponseEntity<Manager> updateManager(Manager manager, Long id){
        Optional<Manager> managerToUpdate = managerRepository.findById(id);
        if (managerToUpdate.isPresent()){
            managerToUpdate.get().setFullName(manager.getFullName());
            managerToUpdate.get().setEmail(manager.getEmail());
            managerToUpdate.get().setDepartment(manager.getDepartment());
            managerToUpdate.get().setPhone(manager.getPhone());

            Manager updatedManager = managerRepository.save(managerToUpdate.get());
            return ResponseEntity.ok(updatedManager); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @Override
    public ResponseEntity<?> deleteManager(Long id) {
        Optional<Manager> manager = managerRepository.findById(id);

        if (manager.isPresent()) {

            Long userId = manager.get().getUser().getId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            user.setType(UserType.USER);
            userRepository.save(user);

            managerRepository.deleteById(id);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }







}
