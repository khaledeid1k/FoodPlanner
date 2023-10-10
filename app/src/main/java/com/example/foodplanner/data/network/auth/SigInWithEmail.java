package com.example.foodplanner.data.network.auth;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.ValidationSate;
import com.example.foodplanner.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SigInWithEmail {
    private FirebaseAuth firebaseAuth;
    AuthView authView;
    ValidationSate validationSate;


    public SigInWithEmail(AuthView authView, ValidationSate validationSate) {
        this.authView = authView;
        this.validationSate = validationSate;
        init();
    }

    void init() {
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void checkStateOfUser(User user) {
        Validation validation = validateUserData(user);
        if (validation.isValid()) {
            authView.resultValidate(validation);
            firebaseAuth.signInWithEmailAndPassword(
                            user.getEmail(),
                            user.getPassword()
                    ).
                    addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()) {
                                    putValueOfUserId(firebaseAuth.getCurrentUser().getUid());
                                    authView.succeed();
                                } else {
                                    authView.failure("Email not register");
                                }
                            }
                    );

        } else {
            authView.resultValidate(validation);
        }

    }

    Validation validateUserData(User user) {
        return validationSate.login(user);
    }

    void putValueOfUserId(String uid) {
        Constants.UserId = uid;
    }

}
