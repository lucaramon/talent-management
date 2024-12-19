package com.example.talent_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.domain.Manager;
import com.example.talent_api.repository.ManagerRepository;
import com.example.talent_api.repository.UserRepository;
import com.example.talent_api.service.ManagerServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
	ManagerRepository managerRepository;

    @Autowired
	UserRepository userRepository;

    @Autowired
    private ManagerServiceImpl managerService;

    @GetMapping
    public List<Manager> getAllManagers(){
        return managerService.getAllManagers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable long id){
        return managerService.getManagerById(id);
    }

    @PostMapping
    public ResponseEntity<Manager> addManager(@RequestBody JsonNode requestBody) {
        return managerService.addManager(requestBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manager> updateManager(@RequestBody Manager manager, @PathVariable long id){
        return managerService.updateManager(manager, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable long id){
        return managerService.deleteManager(id);
    }
    
}
