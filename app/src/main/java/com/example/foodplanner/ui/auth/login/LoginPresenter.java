package com.example.foodplanner.ui.auth.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.Authentication;

public class LoginPresenter {

    private MutableLiveData<Validation> validationMutableLiveData = new MutableLiveData<>();

    public LiveData<Validation> validationLiveDataLogin() {
        return validationMutableLiveData;
    }

    Authentication authentication;

    public LoginPresenter(Authentication authentication) {
        this.authentication = authentication;

    }

    void validation(User user) {
        Log.i("ssssss", "validation: " +user.getEmail() ) ;
        Validation login = authentication.login(user);
            validationMutableLiveData.setValue(login);
    }
}
