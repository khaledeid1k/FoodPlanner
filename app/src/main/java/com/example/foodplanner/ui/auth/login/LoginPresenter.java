package com.example.foodplanner.ui.auth.login;

import static com.example.foodplanner.utils.Constants.RC_SIGN_IN;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.Authentication;
import com.example.foodplanner.utils.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginPresenter {

    private MutableLiveData<Validation> validationMutableLiveData = new MutableLiveData<>();

    public LiveData<Validation> validationLiveDataLogin() {
        return validationMutableLiveData;
    }

    Authentication authentication;
    LoginView loginView;
    public LoginPresenter(Authentication authentication) {
        this.authentication = authentication;

    }

    void validation(User user) {
        Log.i("ssssss", "validation: " +user.getEmail() ) ;
        Validation login = authentication.login(user);
            validationMutableLiveData.setValue(login);
    }
    void checkIfEmailExists(String userEmail, FirebaseFirestore firebaseDatabase) {
        CollectionReference collection = firebaseDatabase.collection(Constants.CollectionPath);
        collection.whereEqualTo(Constants.email, userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        boolean isEmailExists = !result.isEmpty();
                        if (isEmailExists) {
                           loginView.navigateToHome();
                        } else {
                            loginView.logout("Email not register");
                        }
                    } else {
                        loginView.error("Error checking email in fireStore");
                    }

                });
    }

}
