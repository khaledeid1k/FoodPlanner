package com.example.foodplanner.ui.base;

public interface BasePresenterView {

    void showLoading();
    <T>void showData(T data);
    void showError(String errorMessage);


}
