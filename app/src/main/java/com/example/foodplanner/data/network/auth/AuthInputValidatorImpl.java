package com.example.foodplanner.data.network.auth;

import java.util.regex.Pattern;

public class AuthInputValidatorImpl implements AuthInputValidator{
    @Override
    public Boolean passwordValidator(String password) {
     final String PASSWORD_PATTERN =  "^(?=.*[0-9])"
             + "(?=.*[a-z])(?=.*[A-Z])"
             + "(?=.*[@#$%^&+=])"
             + "(?=\\S+$).{8,20}$";
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }

    @Override
    public Boolean usernameValidator(String userName) {
         String USERNAME_PATTERN = "^[a-zA-Z0-9]+$";
        boolean validSyntax = Pattern.compile(USERNAME_PATTERN).matcher(userName).matches();
        boolean validLength= userName.length()>4&&userName.length()<=10;
        return validLength && validSyntax;
    }
}
