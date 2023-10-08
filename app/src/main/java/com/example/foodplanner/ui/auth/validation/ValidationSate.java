package com.example.foodplanner.ui.auth.validation;


import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;

public interface ValidationSate {
    Validation login(User user);
    Validation signUp(User user);

}
