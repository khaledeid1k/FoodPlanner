package com.example.foodplanner.data.network.auth;

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

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class RegisterWithGoogle {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseDatabase;
    AuthView authView;
    Single<FirebaseUser> single;


    public RegisterWithGoogle(AuthView authView) {
        this.authView = authView;
        init();
    }

    void init() {
        firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public Single<Boolean> registerWithGoogle(String idToken) {
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
                                firebaseAuth.signOut();
                                emitter.onError(new Throwable("Email is exit, Can Login in"));
                            } else {
                                String uid = currentUser.getUid();
                                String email = currentUser.getEmail();
                                putValueOfUserId(uid);
                                storeData(email, uid).subscribe(
                                        emitter::onSuccess,
                                        emitter::onError
                                );
                            }
                        } else {
                            firebaseAuth.signOut();
                            emitter.onError(new Throwable("Error checking email in fireStore"));
                        }

                    });
        });
    }

    void putValueOfUserId(String uid) {
        Constants.UserId = uid;
    }


    @NonNull Single<Boolean> storeData(String email, String userId) {
        return Single.create(emitter -> {
            DocumentReference documentReference = firebaseDatabase.collection(
                    Constants.CollectionPath
            ).document(userId);
            Map<String, String> user = new HashMap<>();
            user.put(Constants.email, email);
            user.put(Constants.password, "");
            documentReference.set(user).addOnSuccessListener(task -> {
                        emitter.onSuccess(true);
                    })
                    .addOnFailureListener(task -> {
                        emitter.onError(new Throwable("Failed to sign in with Google"));
                    });

        });

    }

}
