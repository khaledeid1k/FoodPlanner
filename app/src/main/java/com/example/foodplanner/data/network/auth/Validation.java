package com.example.foodplanner.data.network.auth;



public class Validation {
    boolean isValid;
    String message;

    public boolean isValid() {
        return isValid;
    }

    public String getMessage() {
        return message;
    }

    public Validation(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }


}