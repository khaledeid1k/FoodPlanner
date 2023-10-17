package com.example.foodplanner.ui.base;

public interface BasePresenterView <T> {
    void showLoading();
    void showData(T data);
    void showError(String errorMessage);


}
