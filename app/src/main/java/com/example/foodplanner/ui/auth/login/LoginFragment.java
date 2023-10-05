package com.example.foodplanner.ui.auth.login;

import static com.example.foodplanner.utils.Extensions.moveToLoginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class LoginFragment extends Fragment {
    TextInputEditText emailText, passwordText;
    TextInputLayout emailP, passwordP;
    TextView register, loginAsGust;
    AppCompatButton login;
    LoginPresenter loginPresenter;
    FirebaseAuth firebaseAuth;
    ImageView loginBYGoogle;
    GoogleSignInClient mGoogleSignInClient;
    private  final int RC_SIGN_IN = 1;
    private static final String TAG = "LoginFragmentlollllllllll";
    FirebaseFirestore firebaseDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
        checkIfUserLoginBefore();
        loginAction();
        navigateToRegister();
        setLoginAsGust();
        loginBYGoogle.setOnClickListener(viaew -> {
            loginBYGoogle();
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
     startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    void inti(View view) {
        emailText = view.findViewById(R.id.email_value_login);
        passwordText = view.findViewById(R.id.password_value_login);
        emailP = view.findViewById(R.id.email_p);
        passwordP = view.findViewById(R.id.password_p);
        login = view.findViewById(R.id.login_b);
        register = view.findViewById(R.id.go_to_register);
        loginAsGust = view.findViewById(R.id.login_as_gust);
        loginBYGoogle = view.findViewById(R.id.google_login);
        firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        loginPresenter = new LoginPresenter(new AuthenticationImpl(new AuthInputValidatorImpl()));
    }

    void loginAction() {
        login.setOnClickListener(view -> validateData());
    }

    User getData() {
        return new User(emailText.getText().toString().trim(),
                passwordText.getText().toString().trim());
    }

    void validateData() {
        loginPresenter.validation(getData());
        checkData();
    }

    void checkData() {
        loginPresenter.validationLiveDataLogin().observe(getViewLifecycleOwner(),
                validation -> {
                    if (validation.isValid()) {
                        emailP.setErrorEnabled(false);
                        passwordP.setErrorEnabled(false);
                        firebaseAuth.signInWithEmailAndPassword(
                                emailText.getText().toString().trim(),
                                passwordText.getText().toString().trim()).addOnCompleteListener(
                                requireActivity(), task -> {
                                    if (task.isSuccessful()) {
                                        navigateToHome();
                                    } else {


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
            emailP.setErrorEnabled(true);
            passwordP.setErrorEnabled(false);
            emailP.setError(validation.getMessage());
        } else {
            emailP.setErrorEnabled(false);
            passwordP.setErrorEnabled(true);
            passwordP.setError(validation.getMessage());
        }
    }

    void checkIfUserLoginBefore() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            navigateToHome();
        }
    }

    void navigateToHome() {
        Constants.isLogin=true;
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeF);
    }

    void navigateToRegister() {
        register.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_singUpFragment));
    }

    @Override
    public void onStart() {
        super.onStart();
        checkIfUserLoginBefore();

    }

    void setLoginAsGust() {
        loginAsGust.setOnClickListener(view -> {
                navigateToHome();
                Constants.isLogin=false;
    }
        );
    }


    void loginBYGoogle() {

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);

//        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
//            // Now, initiate the sign-in process, which will show the dialog
//            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//            startActivityForResult(signInIntent, RC_SIGN_IN);
//        });

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                String email = currentUser.getEmail();
                Log.i(TAG, "firebaseAuthWithGoogle: " + email);
                checkIfEmailExists(email);

            } else {
                Toast.makeText(requireActivity(), "Failed to sign in with Google.", Toast.LENGTH_LONG).show();
                firebaseAuth.getCurrentUser().delete();
            }
        });
    }

    void checkIfEmailExists(String userEmail) {
        CollectionReference collection = firebaseDatabase.collection(Constants.CollectionPath);
        collection.whereEqualTo(Constants.email, userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        boolean isEmailExists = !result.isEmpty();

                        if (isEmailExists) {
                            navigateToHome();
                        } else {
                            logout();
                            Toast.makeText(requireActivity(), "Email not found in Firestore.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String message = task.getException().getMessage();
                        Log.i(TAG, "Error checking email in Firestore: " + task.getException());
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

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "logout: ");
            } else {
                Log.i(TAG, "Error in logout: ");

            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            Log.d(TAG, "Sign out was successful");
        } else {
            Log.e(TAG, "Sign out failed");
        }
    }
}
