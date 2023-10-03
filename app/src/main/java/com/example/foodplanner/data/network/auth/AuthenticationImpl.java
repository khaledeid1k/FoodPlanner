package com.example.foodplanner.data.network.auth;


import java.util.Objects;

public class AuthenticationImpl implements Authentication {
    AuthInputValidator authInputValidator;
    public AuthenticationImpl(AuthInputValidator authInputValidator) {
        this.authInputValidator = authInputValidator;
    }
    @Override
    public Validation login(User user) {
        if (!authInputValidator.usernameValidator(user.getUserName())) {
            return new Validation(true, "Username not valid");
        } else if (!authInputValidator.passwordValidator(user.getPassword())) {
            return new Validation(true, "Password not valid");
        }

        return new Validation(true, "");
    }

    @Override
    public Validation signUp(User user, String confirmPassword) {
        if (!authInputValidator.usernameValidator(user.getUserName())) {
            return new Validation(true, "Username not valid");
        } else if (!authInputValidator.passwordValidator(user.getPassword())) {
            return new Validation(true, "Password not valid");
        } else if (!Objects.equals(user.getPassword(), confirmPassword)) {
            return new Validation(true, "Password not Equal");
        }
        return new Validation(true, "");
    }




}
