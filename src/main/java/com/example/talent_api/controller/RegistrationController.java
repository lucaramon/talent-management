package com.example.talent_api.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.talent_api.domain.User;
import com.example.talent_api.repository.UserRepository;
import com.example.talent_api.service.UserService;
import com.example.talent_api.service.UserServiceImpl;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userService = new UserServiceImpl(userRepository);
    }
	
    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {

		if (user == null || user.getUsername() == null || user.getPassword() == null || user.getType() == null) {
			return ResponseEntity.status(401).body(null);
		}
		Optional<User> createdUser = userService.createUser(user);
        if (createdUser.isEmpty()) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.status(200).body(createdUser);
        }
    }	

	@GetMapping
    public ResponseEntity<?> health() {
        return ResponseEntity.status(200).body("Registration endpoint");
    } 
    
}