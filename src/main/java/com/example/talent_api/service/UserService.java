package com.example.talent_api.service;

import com.example.talent_api.domain.User;
import java.util.Optional;

public interface UserService {
    Optional<User> createUser(User user);
    Optional<User> updateUser(Long id, User newUser);
    Boolean deleteUser(Long id);
}
