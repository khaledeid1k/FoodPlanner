package com.example.foodplanner.data.models;

public class User {
    private String email;
    private String password;
    private String RePassword;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String rePassword) {
        this.email = email;
        this.password = password;
        RePassword = rePassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRePassword() {
        return RePassword;
    }
}
