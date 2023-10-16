package com.example.foodplanner.data.repository.state;

public  class StateOfResponse<T> {
    T data;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}

