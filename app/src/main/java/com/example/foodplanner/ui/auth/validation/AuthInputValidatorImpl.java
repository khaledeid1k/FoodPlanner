package com.example.foodplanner.ui.auth.validation;

import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.utils.Constants;

import java.util.Objects;
import java.util.regex.Pattern;

public class AuthInputValidatorImpl implements AuthInputValidator{
    @Override
    public Validation passwordValidator(String password,String rePassword) {
        String digitPattern = ".*[0-9].*";
        String lowercasePattern = ".*[a-z].*";
        String uppercasePattern = ".*[A-Z].*";
        String specialCharacterPattern = ".*[@#$%^&+=].*";
        String lengthPattern = ".{8,20}";
        if (!password.matches(digitPattern)) {
           return new Validation(false,"Password must contain at least one digit", Constants.ErrorPassword);
        }
        if (!password.matches(lowercasePattern)) {
            return new Validation(false,"Password must contain at least one lowercase letter.", Constants.ErrorPassword);

        }
        if (!password.matches(uppercasePattern)) {
            return new Validation(false,"Password must contain at least one uppercase letter.", Constants.ErrorPassword);

        }
        if (!password.matches(specialCharacterPattern)) {
            return new Validation(false,"Password must contain at least one special character (@#$%^&+=).", Constants.ErrorPassword);

        }
        if (!password.matches(lengthPattern)) {
            return new Validation(false,"Password length must be between 8 and 20 characters.", Constants.ErrorPassword);
        }
         if (!Objects.equals(password, rePassword)) {
            return new Validation(false, "Password not Equal",Constants.ErrorRePassword);
        }

        return  new Validation(true,"","");
    }

    @Override
    public Validation usernameValidator(String email) {
        String digitPattern = ".*[0-9].*";
        String lowercasePattern = ".*[a-z].*";
        String uppercasePattern = ".*[A-Z].*";
        String specialCharacterPattern = ".*[_-].*";
        boolean lengthPattern = email.matches(".{15,20}");

        if (!email.matches(digitPattern)) {
            return new Validation(false,"Email must contain at least one digit", Constants.ErrorEmail);
        }
        if (!email.matches(lowercasePattern)) {
            return new Validation(false,"Email must contain at least one lowercase letter.", Constants.ErrorEmail);

        }
        if (!email.matches(uppercasePattern)) {
            return new Validation(false,"Email must contain at least one uppercase letter.", Constants.ErrorEmail);

        }
        if (!email.matches(specialCharacterPattern)) {
            return new Validation(false,"Email must contain at least one special character (_-).", Constants.ErrorEmail);
        }

        if (!email.matches(".*@\\w+\\..*")) {
            return new Validation(false, "Invalid placement of '@' symbol.",Constants.ErrorEmail);
        }
        if (!email.contains("@")) {
            return new Validation(false, "Missing '@' symbol.",Constants.ErrorEmail);
        }
        if (!email.endsWith(".com") && !email.endsWith(".org") && !email.endsWith(".net")) {
            return new Validation(false, "Invalid domain. Supported domains are .com, .org, and .net.",Constants.ErrorEmail);
        }

        if (!lengthPattern) {
            return new Validation(false,"Email length must be between 15 and 20 characters.", Constants.ErrorEmail);
        }


       return new Validation(true,"","");
    }
}
