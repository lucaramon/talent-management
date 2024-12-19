package com.example.talent_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.domain.Credentials;
import com.example.talent_api.domain.LoginResponse;
import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.repository.CandidateRepository;
import com.example.talent_api.repository.ManagerRepository;
import com.example.talent_api.repository.UserRepository;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
	UserRepository userRepository;
	
	@Autowired
    ManagerRepository managerRepository;

    @Autowired
    CandidateRepository candidateRepository;
	
    @PostMapping
    public ResponseEntity<LoginResponse> create(@RequestBody Credentials credentials) {
    	User user = userRepository.findByUsername(credentials.getUsername()).orElse(null);;
    	if(user == null) {
    		return ResponseEntity.status(401).body(null);
    	}
    	if(user.getPassword().equalsIgnoreCase(credentials.getPassword() )) {
            Object userTypeObject = null;
            if (user.getType() == UserType.MANAGER) {
                userTypeObject = managerRepository.findManagerByUser(user).orElse(null);
            } else if (user.getType() == UserType.CANDIDATE) {
                userTypeObject = candidateRepository.findCandidateByUser(user).orElse(null);
            }
            LoginResponse loginResponse = new LoginResponse(user, userTypeObject);
            return ResponseEntity.status(200).body(loginResponse);
        }else {
    		return ResponseEntity.status(401).body(null);
    	}
    }
    
    @GetMapping
    public ResponseEntity<?> health() {
        return ResponseEntity.status(200).body("Login endpoint");

    } 
    
}
