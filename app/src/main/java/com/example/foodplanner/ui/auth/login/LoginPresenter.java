package com.example.foodplanner.ui.auth.login;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.data.network.auth.AuthView;
import com.example.foodplanner.data.network.auth.SigInWithEmail;
import com.example.foodplanner.data.network.auth.SignInWithGoogle;
import com.example.foodplanner.ui.auth.validation.ValidationSate;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter implements AuthView {

    ValidationSate validationSate;
    LoginView loginView;
    SignInWithGoogle authWithGoogle;
    StateOfBottomNav stateOfBottomNav;
    SigInWithEmail sigInWithEmail;

    public LoginPresenter(LoginView loginView,
                          ValidationSate validationSate,
                          StateOfBottomNav stateOfBottomNav) {
        this.validationSate = validationSate;
        this.stateOfBottomNav = stateOfBottomNav;
        this.loginView = loginView;

        authWithGoogle = new SignInWithGoogle(this);
        sigInWithEmail = new SigInWithEmail(this, validationSate);

    }

    void loginWithEmail(User user) {
        sigInWithEmail.checkStateOfUser(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (validation) ->  loginView.resultValidate(validation),
                        error -> loginView.failureLogin(error.getMessage()),
                        ()-> loginView.succeedLogin()
                );
    }

    void loginWithGoogle(String idToken) {
        authWithGoogle.signInWithGoogle(idToken).subscribeOn(
                        Schedulers.io()
                ).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        canLogin -> loginView.succeedLogin(),
                        error -> loginView.failureLogin(error.getMessage())
                );
    }


    void loginAsGust() {
        loginView.loginAsGust();
    }


    @Override
    public void checkIfUserLoginBefore(boolean state) {
        if (state) {
            loginView.succeedLogin();
        } else {
            loginView.failureLogin("");
        }
    }

}
