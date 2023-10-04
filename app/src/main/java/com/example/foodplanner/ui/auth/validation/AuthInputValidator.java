package com.example.foodplanner.ui.auth.validation;

import com.example.foodplanner.data.models.Validation;

public interface AuthInputValidator {
    Validation passwordValidator(String password,String rePassword);
    Validation usernameValidator(String userName);
}
