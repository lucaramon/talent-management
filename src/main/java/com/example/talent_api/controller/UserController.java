package com.example.talent_api.controller;

import com.example.talent_api.domain.User;
import com.example.talent_api.repository.UserRepository;
import com.example.talent_api.service.UserService;
import com.example.talent_api.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = new UserServiceImpl(userRepository);
    }

    @GetMapping
    public ArrayList<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Optional<User> createdUser = userService.createUser(user);
        if (createdUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser.get());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser) {
        Optional<User> updatedUser = userService.updateUser(id, newUser);
        if (updatedUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(updatedUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteUserById(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
