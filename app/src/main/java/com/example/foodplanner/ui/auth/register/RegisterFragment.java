package com.example.foodplanner.ui.auth.register;

import static com.example.foodplanner.utils.Constants.RC_SIGN_UP;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.example.foodplanner.ui.auth.validation.ValidationSateImpl;
import com.example.foodplanner.ui.base.BaseFragment;
import com.example.foodplanner.utils.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterFragment  extends BaseFragment implements SingUpView {
    TextInputEditText emailText, passwordText, repeatedPasswordText;
    TextInputLayout emailText_P, passwordText_P, repeatedPasswordText_P;
    AppCompatButton register;
    TextView login;
    ImageView googleRegister;

    RegisterPresenter registerPresenter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseDatabase;
    private ProgressDialog progressDialog;
    GoogleSignInClient googleSignInClient;
    @Override
    protected int getLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
        registerWithEmail();
        registerByGoogle();
        navigateToLogin();
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
        progressDialog = new ProgressDialog(requireActivity());
        firebaseDatabase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        registerPresenter = new RegisterPresenter(new ValidationSateImpl(new AuthInputValidatorImpl()
        ),this
                );

    }




    void registerWithEmail() {
        register.setOnClickListener(view ->
                registerPresenter.registerWithEmail(getUserData()));
    }


    User getUserData() {
        return new User(emailText.getText().toString().trim(),
                passwordText.getText().toString().trim()
                , repeatedPasswordText.getText().toString().trim()
        );
    }

    void registerByGoogle() {
        googleRegister.setOnClickListener(view -> {
             googleSignInClient = configureGoogleAuth();
            Intent  registerIntent=  googleSignInClient.getSignInIntent();
            startActivityForResult( registerIntent, RC_SIGN_UP);
        });
    }

    void navigateToLogin() {
        login.setOnClickListener(view -> Navigation.findNavController(view).popBackStack());
    }

    GoogleSignInClient configureGoogleAuth() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(requireActivity(), googleSignInOptions);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_UP) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                progressDialog.setMessage(getString(R.string.loading_sign_Up));
                progressDialog.show();
                if (account != null) {
                    registerPresenter.registerWithGoogle(account.getIdToken());
                }
            } catch (ApiException ignored) {
            }
        }
    }

    @Override
    public void succeedRegister() {
        progressDialog.dismiss();
        moveToHome();
    }

    @Override
    public void failureRegister(String message) {
        if(googleSignInClient!=null){
            googleSignInClient.signOut();
        }
        progressDialog.dismiss();
        if (!message.isEmpty()) {
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void resultValidate(Validation validation) {
        if(validation.isValid()){
            progressDialog.setMessage(getString(R.string.loading_sign_Up));
            progressDialog.show();
            emailText_P.setErrorEnabled(false);
            passwordText_P.setErrorEnabled(false);
            repeatedPasswordText_P.setErrorEnabled(false);
        }else {
            printError(validation);

        }
    }
    void printError(Validation validation){
        switch (validation.getType()) {
            case Constants.ErrorEmail:
                emailText_P.setErrorEnabled(true);
                passwordText_P.setErrorEnabled(false);
                repeatedPasswordText_P.setErrorEnabled(false);
                emailText_P.setError(validation.getMessage());
                break;
            case Constants.ErrorPassword:
                passwordText_P.setErrorEnabled(true);
                emailText_P.setErrorEnabled(false);
                repeatedPasswordText_P.setErrorEnabled(false);
                passwordText_P.setError(validation.getMessage());
                break;
            case Constants.ErrorRePassword:
                repeatedPasswordText_P.setErrorEnabled(true);
                passwordText_P.setErrorEnabled(false);
                emailText_P.setErrorEnabled(false);
                repeatedPasswordText_P.setError(validation.getMessage());
                break;
        }

    }

    void moveToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_singUpFragment_to_homeF);
    }
}