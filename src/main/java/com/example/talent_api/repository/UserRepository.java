package com.example.talent_api.repository;

import com.example.talent_api.domain.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import java.util.ArrayList;


public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    @NonNull
    ArrayList<User> findAll();

    Optional<User> findByUsername(String username);
}