package com.example.foodplanner.data.network.auth;

public interface AuthInputValidator {
    Boolean passwordValidator(String password);
    Boolean usernameValidator(String userName);
}
