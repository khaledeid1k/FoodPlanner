package com.example.foodplanner.data.network.auth;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.ValidationSate;
import com.example.foodplanner.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthWithEmail {
    private FirebaseAuth firebaseAuth;
    AuthView authView;
    ValidationSate validationSate;


    public AuthWithEmail( AuthView authView, ValidationSate validationSate) {
        this.authView = authView;
        this.validationSate = validationSate;
        init();
    }
    void init(){
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public   void checkStateOfUser(  User user) {
        Validation validation = validateUserData(user);
        if (validation.isValid()) {
            authView.validate(validation);
//                        progressDialog.setMessage(getString(R.string.loading_login));
//                        progressDialog.show();
//                        emailP.setErrorEnabled(false);
//                        passwordP.setErrorEnabled(false);

                        firebaseAuth.signInWithEmailAndPassword(

                                        user.getEmail(),
                                        user.getPassword()
                                ).

                                addOnCompleteListener(
                                         task -> {
                                            if (task.isSuccessful()) {
                                                authView.succeed();
                                            } else {
                                                authView.failure("Email not register");
                                            }
                                    //        progressDialog.dismiss();

                                        }
                                );

                    } else {
                        authView.validate(validation);

                    }

    }

    Validation validateUserData(User user){
        return  validationSate.login(user);
    }
    public   void checkIfUserLoginBefore() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            putValueOfUserId(currentUser);
            authView.checkIfUserLoginBefore(true);
        }else {
            authView.checkIfUserLoginBefore(false);

        }
    }
    void putValueOfUserId(  FirebaseUser currentUser){
        Constants.UserId = currentUser.getUid();
    }

}
