package com.example.foodplanner.ui.auth.login;

public interface LoginView {
    void navigateToHome();
    void logout(String message);
    void error(String message);
}
