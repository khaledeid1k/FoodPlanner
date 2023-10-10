package com.example.foodplanner.ui.auth.login;

import com.example.foodplanner.data.models.Validation;

public interface LoginView {
    void succeedLogin();
    void failureLogin(String message);
    void loginAsGust();
    void resultValidate(Validation validation);

}
