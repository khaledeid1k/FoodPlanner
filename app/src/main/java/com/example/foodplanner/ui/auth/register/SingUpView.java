package com.example.foodplanner.ui.auth.register;

import com.example.foodplanner.data.models.Validation;

public interface SingUpView {
    void succeedRegister();
    void failureRegister(String message);
    void resultValidate(Validation validation);
}
