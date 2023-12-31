package com.example.foodplanner.data.network.auth;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.ValidationSate;
import com.example.foodplanner.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class SigInWithEmail {
    private FirebaseAuth firebaseAuth;
    AuthView authView;
    ValidationSate validationSate;
    Observable<Validation> observable;



    public SigInWithEmail(AuthView authView, ValidationSate validationSate) {
        this.authView = authView;
        this.validationSate = validationSate;
        init();
    }

    void init() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public @NonNull Observable<Validation> checkStateOfUser(User user) {
        Validation validation = validateUserData(user);
        observable= Observable.create(emitter -> {
            if (validation.isValid()) {
                emitter.onNext(validation);
                firebaseAuth.signInWithEmailAndPassword(
                                user.getEmail(),
                                user.getPassword()
                        ).
                        addOnCompleteListener(
                                task -> {
                                    if (task.isSuccessful()) {
                                        putValueOfUserId(firebaseAuth.getCurrentUser().getUid());
                                        emitter.onComplete();
                                    } else {
                                        emitter.onError(new Throwable("Email not register"));
                                    }
                                }
                        );

            } else {
                emitter.onNext(validation);


            }
        });
     return observable;
    }

    Validation validateUserData(User user) {
        return validationSate.login(user);
    }

    void putValueOfUserId(String uid) {
        Constants.UserId = uid;
    }

}
