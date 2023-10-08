package com.example.foodplanner.ui.auth.login;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.airbnb.lottie.L;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.data.network.auth.AuthView;
import com.example.foodplanner.data.network.auth.AuthWithEmail;
import com.example.foodplanner.data.network.auth.AuthWithGoogle;
import com.example.foodplanner.ui.auth.validation.ValidationSate;
import com.example.foodplanner.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter implements AuthView {

    ValidationSate validationSate;
    LoginView loginView;
    AuthWithGoogle authWithGoogle;
     StateOfAuth stateOfAuth;
    //AuthWithEmail authWithEmail;
    public LoginPresenter(LoginView loginView,
            ValidationSate validationSate,
                          StateOfAuth stateOfAuth ) {
        this.validationSate = validationSate;
        this.stateOfAuth = stateOfAuth;
        this.loginView = loginView;

         authWithGoogle = new AuthWithGoogle(this);
      //  authWithEmail = new AuthWithEmail(this,validationSate);

    }
//    void loginWithEmail(User user){
//        authWithEmail.checkStateOfUser(user);
//    }
    void loginWithGoogle(String idToken){
        authWithGoogle.signInWithGoogle(idToken);
    }


    void loginAsGust() {
       loginView.loginAsGust();
        Constants.UserId = "";
    }

    public void logoutFormEmailLogin() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }


    @Override
    public void succeed() {
        loginView.succeedLogin();
    }

    @Override
    public void failure(String message) {
        loginView.failureLogin(message);
    }

    @Override
    public void checkIfUserLoginBefore(boolean state) {
        if(state){loginView.succeedLogin();}else {loginView.failureLogin("");}
    }

    @Override
    public void validate(Validation validation) {

    }
}
