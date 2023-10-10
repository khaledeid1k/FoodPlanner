package com.example.foodplanner.data.network.auth;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.utils.Constants;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterWithGoogle {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseDatabase;
    AuthView authView;

    public RegisterWithGoogle(AuthView authView) {
        this.authView = authView;
        init();
    }

    void init() {
        firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registerWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,
                null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                checkIfEmailExists(currentUser);
            } else {
                authView.failure("Failed to sign in with Google.");
            }
        });
    }

    void checkIfEmailExists(FirebaseUser currentUser) {
        CollectionReference collection = firebaseDatabase.collection(Constants.CollectionPath);
        collection.whereEqualTo(Constants.email, currentUser.getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        boolean isEmailExists = !result.isEmpty();
                        if (isEmailExists) {
                            firebaseAuth.signOut();
                            authView.failure("Email is exit, Can Login in");
                        } else {
                            String uid = currentUser.getUid();
                            String email = currentUser.getEmail();
                            putValueOfUserId(uid);
                            storeData(email, uid);
                            authView.succeed();
                        }
                    } else {
                        firebaseAuth.signOut();
                        authView.failure("Error checking email in fireStore");
                    }

                });
    }

    void putValueOfUserId(String uid) {
        Constants.UserId = uid;
    }



    void storeData(String email, String userId) {
        DocumentReference documentReference = firebaseDatabase.collection(
                Constants.CollectionPath
        ).document(userId);
        Map<String, String> user = new HashMap<>();
        user.put(Constants.email, email);
        user.put(Constants.password, "");
        documentReference.set(user).addOnSuccessListener(unused -> {
        });
    }

}
