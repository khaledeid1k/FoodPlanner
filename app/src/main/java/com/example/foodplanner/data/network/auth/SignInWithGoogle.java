package com.example.foodplanner.data.network.auth;

import com.example.foodplanner.utils.Constants;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class SignInWithGoogle {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseDatabase;
    AuthView authView;
    Single<FirebaseUser> single;

    public SignInWithGoogle(AuthView authView) {
        this.authView = authView;
        init();
        checkIfUserLoginBefore();
    }

    void init() {
        firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public Single<Boolean> signInWithGoogle(String idToken) {
         single = Single.create(emitter -> {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken,
                    null);
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(firebaseAuth.getCurrentUser());
                } else {
                    emitter.onError(new Throwable("Failed to sign in with Google."));
                }
            });
        });
       return single.flatMap(this::checkIfEmailExists);


    }

    Single<Boolean> checkIfEmailExists(FirebaseUser currentUser) {
        return Single.create(emitter -> {
            CollectionReference collection = firebaseDatabase.collection(Constants.CollectionPath);
            collection.whereEqualTo(Constants.email, currentUser.getEmail())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot result = task.getResult();
                            boolean isEmailExists = !result.isEmpty();
                            if (isEmailExists) {
                                putValueOfUserId(currentUser.getUid());
                                emitter.onSuccess(true);
                            } else {
                                currentUser.delete();
                                emitter.onError(new Throwable("Email not register"));
                            }
                        } else {
                            currentUser.delete();
                            emitter.onError((new Throwable("Error checking email in fireStore")));
                        }

                    });
        });
    }

    void putValueOfUserId(String uid) {
        Constants.UserId = uid;
    }


    void checkIfUserLoginBefore() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            putValueOfUserId(currentUser.getUid());
            authView.checkIfUserLoginBefore(true);
        } else {
            authView.checkIfUserLoginBefore(false);

        }
    }


}
