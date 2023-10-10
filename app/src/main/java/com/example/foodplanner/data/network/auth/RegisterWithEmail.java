package com.example.foodplanner.data.network.auth;

import android.widget.Toast;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.ValidationSate;
import com.example.foodplanner.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterWithEmail {
    private FirebaseAuth firebaseAuth;
    AuthView authView;
    ValidationSate validationSate;
    private FirebaseFirestore firebaseDatabase;



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

    public void checkStateOfUser(User user) {
        Validation validation = validateUserData(user);
        if (validation.isValid()) {
            authView.resultValidate(validation);
            firebaseAuth.createUserWithEmailAndPassword(
                    user.getEmail(), user.getPassword()
            ).addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            String uid = currentUser.getUid();
                            putValueOfUserId(uid);
                            storeData(user, uid);
                            authView.succeed();
                        }else {
                            authView.failure("Email is register before, Please Login in.");
                        }
                    }

            );
        } else {
            authView.resultValidate(validation);

        }



    }
    void storeData(User userData, String userId) {
        DocumentReference documentReference = firebaseDatabase.collection(
                Constants.CollectionPath
        ).document(userId);
        Map<String, String> user = new HashMap<>();
        user.put(Constants.email, userData.getEmail());
        user.put(Constants.password, userData.getPassword());
        documentReference.set(user).addOnSuccessListener(unused -> {
        });
    }



}
