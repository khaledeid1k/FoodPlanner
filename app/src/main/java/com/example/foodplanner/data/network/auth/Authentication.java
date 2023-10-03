package com.example.foodplanner.data.network.auth;



public interface Authentication {
    Validation login(User user);
    Validation signUp(User user,String ConfirmPassword);

}
