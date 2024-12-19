package com.example.talent_api.service;

import com.example.talent_api.domain.User;
import com.example.talent_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> createUser(User user) {
        Optional<User> userCheck = userRepository.findByUsername(user.getUsername());
        if (userCheck.isPresent() && userCheck.get().getUsername().equals(user.getUsername())) {
            return Optional.empty();
        }
        User created = userRepository.save(user);
        return Optional.of(created);
    }

    public Optional<User> updateUser(Long id, User newUser) {
        Optional<User> toUpdate = userRepository.findById(id);
        if (toUpdate.isPresent()) {
            toUpdate.get().setUsername(newUser.getUsername());
            toUpdate.get().setPassword(newUser.getPassword());
            toUpdate.get().setType(newUser.getType());
            return Optional.of(userRepository.save(toUpdate.get()));
        } else {
            return Optional.empty();
        }
    }

    public Boolean deleteUser(Long id) {
        Optional<User> toDelete = userRepository.findById(id);
        if (toDelete.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else return false;
    }
}
