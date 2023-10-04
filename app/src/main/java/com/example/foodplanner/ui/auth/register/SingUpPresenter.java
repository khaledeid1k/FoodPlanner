package com.example.foodplanner.ui.auth.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.Authentication;
import com.google.firebase.auth.FirebaseAuth;

public class SingUpPresenter {
    Authentication  authentication;

    private MutableLiveData<Validation> validationMutableLiveData = new MutableLiveData<>();
    public LiveData<Validation> validationLiveDataRegister() {
        return validationMutableLiveData;
    }
    public SingUpPresenter(Authentication authentication) {
        this.authentication = authentication;

    }


    void validation(User user) {
        Validation login = authentication.signUp(user);
            validationMutableLiveData.setValue(login);

    }



}
