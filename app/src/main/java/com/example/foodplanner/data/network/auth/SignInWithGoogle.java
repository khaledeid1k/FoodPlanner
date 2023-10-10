package com.example.foodplanner.data.network.auth;

import com.example.foodplanner.utils.Constants;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SignInWithGoogle {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseDatabase;
    AuthView authView;
    public SignInWithGoogle(AuthView authView){
        this.authView=authView;
        init();
        checkIfUserLoginBefore();
    }

    void  init(){
        firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signInWithGoogle(String idToken) {
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
                            putValueOfUserId(currentUser.getUid());
                            authView.succeed();
                        } else {
                            currentUser.delete();
                            authView.failure("Email not register");
                        }
                    } else {
                        currentUser.delete();
                        authView.failure("Error checking email in fireStore");
                    }

                });
    }

    void putValueOfUserId(  String uid){
        Constants.UserId = uid;
    }


    void checkIfUserLoginBefore() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            putValueOfUserId(currentUser.getUid());
            authView.checkIfUserLoginBefore(true);
        }else {
            authView.checkIfUserLoginBefore(false);

        }
    }



}
