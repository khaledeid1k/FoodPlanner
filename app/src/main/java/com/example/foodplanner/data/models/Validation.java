package com.example.foodplanner.data.models;



public class Validation {
    boolean isValid;
    String message;
    String type;

    public String getType() {
        return type;
    }

    public Validation(boolean isValid, String message, String type) {
        this.isValid = isValid;
        this.message = message;
        this.type = type;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMessage() {
        return message;
    }




}