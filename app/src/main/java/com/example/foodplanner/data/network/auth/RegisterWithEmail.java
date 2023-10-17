package com.example.foodplanner.data.network.auth;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.ValidationSate;
import com.example.foodplanner.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class RegisterWithEmail {
    private FirebaseAuth firebaseAuth;
    AuthView authView;
    ValidationSate validationSate;
    private FirebaseFirestore firebaseDatabase;
    Completable completable;


    public RegisterWithEmail(AuthView authView, ValidationSate validationSate) {
        this.authView = authView;
        this.validationSate = validationSate;
        init();
    }

    void init() {
        firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    Validation validateUserData(User user) {
        return validationSate.login(user);
    }



    void putValueOfUserId(String uid) {
        Constants.UserId = uid;
    }

    public Completable checkStateOfUser(User user) {
        Validation validation = validateUserData(user);
        if (validation.isValid()) {
            authView.resultValidate(validation);
            completable= Completable.create(emitter ->
            firebaseAuth.createUserWithEmailAndPassword(
                    user.getEmail(), user.getPassword()
            ).addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            String uid = currentUser.getUid();
                            putValueOfUserId(uid);
                            storeData(user, uid).subscribe(
                                    e-> emitter.onComplete()
                            );

                        }else {
                            emitter.onError(new Throwable("Email is register before, Please Login in."));
                        }
                    }
            )
            );
        } else {
            authView.resultValidate(validation);

        }

return completable;

    }
    Single<Boolean> storeData(User userData, String userId) {
        return Single.create(emitter -> {
        DocumentReference documentReference = firebaseDatabase.collection(
                Constants.CollectionPath
        ).document(userId);
        Map<String, String> user = new HashMap<>();
        user.put(Constants.email, userData.getEmail());
        user.put(Constants.password, userData.getPassword());
        documentReference.set(user).addOnSuccessListener(unused -> {
            emitter.onSuccess(true);
        })    .addOnFailureListener(task -> {
            emitter.onError(new Throwable("Failed to sign in with Google"));
        });

        });
    }



}
