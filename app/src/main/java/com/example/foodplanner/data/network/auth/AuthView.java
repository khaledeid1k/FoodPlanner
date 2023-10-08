package com.example.foodplanner.data.network.auth;

import com.example.foodplanner.data.models.Validation;

public interface AuthView {
    void succeed();
    void failure(String message);
    void checkIfUserLoginBefore(boolean state);
    void validate(Validation validation);
}
