package com.example.foodplanner.ui.auth.validation;


import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.utils.Constants;

import java.util.Objects;

public class AuthenticationImpl implements Authentication {
    AuthInputValidator authInputValidator;

    public AuthenticationImpl(AuthInputValidator authInputValidator) {
        this.authInputValidator = authInputValidator;
    }

    @Override
    public Validation login(User user) {
        if (authInputValidator.usernameValidator(user.getEmail()).isValid()) {
            return authInputValidator.passwordValidator(user.getPassword(), user.getPassword());
        }
        return authInputValidator.usernameValidator(user.getEmail());


    }

    @Override
    public Validation signUp(User user) {

        if (authInputValidator.usernameValidator(user.getEmail()).isValid()) {
            return authInputValidator.passwordValidator(user.getPassword(), user.getRePassword());
        }
        return authInputValidator.usernameValidator(user.getEmail());


    }


}
