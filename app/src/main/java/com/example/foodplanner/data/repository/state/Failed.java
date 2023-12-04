package com.example.foodplanner.data.repository.state;

public class Failed<T> extends StateOfResponse<T> {
    String message;

    public String getMessage() {
        return message;
    }

    public Failed(String message) {
        this.message = message;
    }
}
