package com.example.talent_api.domain;

public enum UserType { USER, CANDIDATE, MANAGER, ADMIN;
    public String nameLowerCase() {
        return name().toLowerCase();
    }
}
