package com.example.foodplanner.ui.auth.register;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.data.network.auth.AuthView;
import com.example.foodplanner.data.network.auth.RegisterWithEmail;
import com.example.foodplanner.data.network.auth.RegisterWithGoogle;
import com.example.foodplanner.ui.auth.validation.ValidationSate;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterPresenter implements AuthView {
    ValidationSate validationSate;
    SingUpView singUpView;
    RegisterWithGoogle registerWithGoogle;
    RegisterWithEmail registerWithEmail;

    public RegisterPresenter(
                          ValidationSate validationSate,
                          SingUpView singUpView) {
        this.validationSate = validationSate;
        this.singUpView = singUpView;
        registerWithGoogle = new RegisterWithGoogle(this);
        registerWithEmail = new RegisterWithEmail(this,validationSate);

    }
    void registerWithEmail(User user){
        registerWithEmail.checkStateOfUser(user).subscribeOn(
                Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe(
                ()-> singUpView.succeedRegister(),
                e-> singUpView.failureRegister(e.getMessage())
        );
    }

    void registerWithGoogle(String idToken){
        registerWithGoogle.registerWithGoogle(idToken).subscribeOn(
                Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
        ).subscribe(
                s-> singUpView.succeedRegister(),
                e-> singUpView.failureRegister(e.getMessage())
        );
    }


    @Override
    public void checkIfUserLoginBefore(boolean state) {

    }

    @Override
    public void resultValidate(Validation validation) {
        singUpView.resultValidate(validation);
    }
}
