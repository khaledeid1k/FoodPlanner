package com.example.foodplanner.ui.auth.register;

import static com.example.foodplanner.utils.Extensions.moveToLoginScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.AuthInputValidatorImpl;
import com.example.foodplanner.ui.auth.validation.AuthenticationImpl;
import com.example.foodplanner.utils.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

public class SingUpFragment extends Fragment {
    TextInputEditText emailText, passwordText, repeatedPasswordText;
    TextInputLayout emailText_P, passwordText_P, repeatedPasswordText_P;
    AppCompatButton register;
    TextView login;
    ImageView googleRegister;
    SingUpPresenter registerPresenter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseDatabase;
    String userId;
    private  final int RC_SIGN_IN = 2;
    GoogleSignInClient mGoogleSignInClient;
    String TAG="SingUpFragmentlollllllllll";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
        registerAction();
        backToLogin();

        googleRegister.setOnClickListener(viaew -> {
            loginBYGoogle();
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    void inti(View view) {
        emailText = view.findViewById(R.id.email_value_register);
        passwordText = view.findViewById(R.id.password_value_register);
        repeatedPasswordText = view.findViewById(R.id.repeated_password_value_register);
        register = view.findViewById(R.id.register_b_register);
        login = view.findViewById(R.id.go_to_login);
        emailText_P = view.findViewById(R.id.email_p_register);
        passwordText_P = view.findViewById(R.id.password_p_register);
        repeatedPasswordText_P = view.findViewById(R.id.confirm_password_p_register);
        googleRegister = view.findViewById(R.id.google_register);
        firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        registerPresenter = new SingUpPresenter(new AuthenticationImpl(new AuthInputValidatorImpl()));

    }

    void registerAction() {
        register.setOnClickListener(view -> validateData());
    }

    User getData() {
        return new User(emailText.getText().toString().trim(),
                passwordText.getText().toString().trim()
                , repeatedPasswordText.getText().toString().trim()
        );
    }

    void validateData() {
        registerPresenter.validation(getData());
        checkData();
    }

    void checkData() {
        registerPresenter.validationLiveDataRegister().observe(getViewLifecycleOwner(),
                validation -> {
                    if (validation.isValid()) {
                        emailText_P.setErrorEnabled(false);
                        passwordText_P.setErrorEnabled(false);
                        repeatedPasswordText_P.setErrorEnabled(false);
                        String email = emailText.getText().toString().trim();
                        String password = passwordText.getText().toString().trim();
                        firebaseAuth.createUserWithEmailAndPassword(
                                email, password
                        ).addOnCompleteListener(
                                requireActivity(), task -> {
                                    if (task.isSuccessful()) {
                                        checkIfEmailExists(email,password);

                                    }

                                }
                        );
                    } else {
                        printError(validation);

                    }


                });


    }

    private void printError(Validation validation) {
        if (validation.getType().equals(Constants.ErrorEmail)) {
            emailText_P.setErrorEnabled(true);
            passwordText_P.setErrorEnabled(false);
            repeatedPasswordText_P.setErrorEnabled(false);
            emailText_P.setError(validation.getMessage());
        } else if (validation.getType().equals(Constants.ErrorPassword)) {
            passwordText_P.setErrorEnabled(true);
            emailText_P.setErrorEnabled(false);
            repeatedPasswordText_P.setErrorEnabled(false);
            passwordText_P.setError(validation.getMessage());
        } else {
            repeatedPasswordText_P.setErrorEnabled(true);
            passwordText_P.setErrorEnabled(false);
            emailText_P.setErrorEnabled(false);
            repeatedPasswordText_P.setError(validation.getMessage());
        }
    }

    void navigateToHome() {
        Constants.isLogin=true;
        Navigation.findNavController(requireView()).navigate(R.id.action_singUpFragment_to_homeF);

    }

    void backToLogin() {
        login.setOnClickListener(view -> Navigation.findNavController(view).popBackStack());
    }

    void storeData(String email, String password) {
        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseDatabase.collection(
                Constants.CollectionPath
        ).document(userId);
        Map<String, String> user = new HashMap<>();
        user.put(Constants.email, email);
        user.put(Constants.password, password);
        documentReference.set(user).addOnSuccessListener(unused -> {
            Log.i(TAG, "storeData: ");

        });
    }


    void loginBYGoogle() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);



    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                String email = currentUser.getEmail();
                Log.i(TAG, "firebaseAuthWithGoogle: " + email);
                checkIfEmailExists(email,"");

            } else {
                Toast.makeText(requireActivity(), "Failed to sign in with Google.", Toast.LENGTH_LONG).show();
                firebaseAuth.getCurrentUser().delete();
            }
        });
    }

    void checkIfEmailExists(String userEmail,String password) {
        CollectionReference collection = firebaseDatabase.collection(Constants.CollectionPath);
        collection.whereEqualTo(Constants.email, userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        boolean isEmailExists = !result.isEmpty();
                        if (isEmailExists) {
                            logout();
                            Toast.makeText(requireActivity(), "Email is exit, Can Login in ", Toast.LENGTH_LONG).show();
                        } else {
                            storeData(userEmail, password);
                            navigateToHome();
                        }
                    } else {
                        Log.i(TAG, "Error checking email in FireStore: " + task.getException());
                    }
                });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    // Get the ID token and proceed with Firebase authentication
                    firebaseAuthWithGoogle(account.getIdToken());

                }
            } catch (ApiException ignored) {
            }
        }
    }
    public void logout() {

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {

                moveToLoginScreen( navHostFragment.getNavController());
                Log.i(TAG, "logout: ");
            } else {
                Log.i(TAG, "Error in logout: ");

            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            moveToLoginScreen( navHostFragment.getNavController());
            Log.d(TAG, "Sign out was successful");
        } else {
            Log.e(TAG, "Sign out failed");
        }
    }
}