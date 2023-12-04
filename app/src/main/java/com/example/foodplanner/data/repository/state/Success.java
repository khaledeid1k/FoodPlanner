package com.example.foodplanner.data.repository.state;

public class Success<T> extends StateOfResponse<T> {
    T data;

    public T getData() {
        return data;
    }

    public Success(T data) {
        this.data = data;
    }
}
