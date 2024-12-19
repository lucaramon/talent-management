package com.example.talent_api.domain;

public class LoginResponse {
    private User user;
    private Object userTypeObject;

    public LoginResponse(User user, Object userTypeObject) {
        this.user = user;
        this.userTypeObject = userTypeObject;
    }

    public User getUser() {
        return user;
    }

    public Object getuserTypeObject() {
        return userTypeObject;
    }
}

