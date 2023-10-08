package com.example.foodplanner.ui.auth.login;

import static com.example.foodplanner.utils.Constants.RC_SIGN_IN;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.AuthView;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginFragment extends Fragment {
    TextInputEditText emailText, passwordText;
    TextInputLayout emailP, passwordP;
    TextView register, loginAsGust;

    private AppCompatButton login;
    private  LoginPresenter loginPresenter;
    private  FirebaseAuth firebaseAuth;
    private  ImageView loginBYGoogle;
    private  GoogleSignInClient mGoogleSignInClient;
    private   FirebaseFirestore firebaseDatabase;
    private ProgressDialog progressDialog;
    private  AuthView authView;

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
        loginWithEmail();
        navigateToRegister();
        setLoginAsGust();
        loginWithGoogle();
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
        progressDialog = new ProgressDialog(requireActivity());
        authView=(AuthView)requireActivity() ;

        firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        loginPresenter = new LoginPresenter(new AuthenticationImpl(new AuthInputValidatorImpl()));
    }

    void loginWithGoogle(){
        loginBYGoogle.setOnClickListener(v -> {
            loginBYGoogle();
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    void loginWithEmail() {
        login.setOnClickListener(view -> validateData());
    }

    User getUserData() {
        return new User(emailText.getText().toString().trim(),
                passwordText.getText().toString().trim());
    }

    void validateData() {
        loginPresenter.validation(getUserData());
        checkStateOfUser();
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

    @Override
    public void onStart() {
        super.onStart();
        checkIfUserLoginBefore();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                progressDialog.setMessage("Logging in...");
                progressDialog.show();
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException ignored) {
            }
        }
    }



    public void logout() {
        Constants.UserId="";
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }
    void checkStateOfUser() {
        loginPresenter.validationLiveDataLogin().observe(getViewLifecycleOwner(),
                validation -> {
                    if (validation.isValid()) {

                        progressDialog.setMessage("Logging in...");
                        progressDialog.show();
                        emailP.setErrorEnabled(false);
                        passwordP.setErrorEnabled(false);

                        firebaseAuth.signInWithEmailAndPassword(

                                emailText.getText().toString().trim(),
                                passwordText.getText().toString().trim()).

                                addOnCompleteListener(
                                requireActivity(), task -> {
                                    if (task.isSuccessful()) {
                                        navigateToHome();
                                    } else {
                                        Toast.makeText(requireActivity(), "Email not register", Toast.LENGTH_LONG).show();
                                    }
                                    progressDialog.dismiss();

                                }
                        );

                    } else {
                        printError(validation);

                    }


                });
    }
    void checkIfUserLoginBefore() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Constants.isLogin=true;
            Constants.UserId=firebaseAuth.getCurrentUser().getUid();
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeF);
        }
    }
    void navigateToHome() {

        Constants.isLogin=true;
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeF);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Constants.UserId=currentUser.getUid();
        }
        authView.IsLogin(true);
    }
    void navigateToRegister() {
        register.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_singUpFragment));
    }
    void setLoginAsGust() {
        loginAsGust.setOnClickListener(view -> {
                navigateToHome();
                    Constants.UserId="";
                    Constants.isLogin=false;
                    authView.IsLogin(false);
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


    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                String email = currentUser.getEmail();
                checkIfEmailExists(email);
                progressDialog.dismiss();
            } else {
                Toast.makeText(requireActivity(), "Failed to sign in with Google.", Toast.LENGTH_LONG).show();
                firebaseAuth.getCurrentUser().delete();
            }
        });
    }


}
