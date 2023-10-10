package com.example.foodplanner.ui.auth.login;

import static com.example.foodplanner.utils.Constants.RC_SIGN_IN;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;

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

public class LoginFragment extends BaseFragment implements LoginView {
    private TextInputEditText emailText, passwordText;
    private TextInputLayout emailP, passwordP;
    private TextView register, loginAsGust;
    private AppCompatButton login;
    private ImageView loginByGoogle;
    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter;
    private StateOfBottomNav stateOfBottomNav;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
    }

    void inti(View view) {
        emailText = view.findViewById(R.id.email_value_login);
        passwordText = view.findViewById(R.id.password_value_login);
        emailP = view.findViewById(R.id.email_p);
        passwordP = view.findViewById(R.id.password_p);
        login = view.findViewById(R.id.login_b);
        register = view.findViewById(R.id.go_to_register);
        loginAsGust = view.findViewById(R.id.login_as_gust);
        loginByGoogle = view.findViewById(R.id.google_login);
        progressDialog = new ProgressDialog(requireActivity());
        stateOfBottomNav = (StateOfBottomNav) requireActivity();
    }


    void loginWithEmail() {
        login.setOnClickListener(view -> {
            loginPresenter.loginWithEmail(getUserData());
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

    User getUserData() {
        return new User(emailText.getText().toString().trim(),
                passwordText.getText().toString().trim());
    }


    @Override
    public void onStart() {
        super.onStart();
        stateOfBottomNav.isGust(false);
        loginPresenter = new LoginPresenter(this, new ValidationSateImpl(
                new AuthInputValidatorImpl()), stateOfBottomNav);
        loginWithEmail();
        navigateToRegister();
        setLoginAsGust();
        loginByGoogle();
    }

    void loginByGoogle() {
        loginByGoogle.setOnClickListener(v -> {
            googleSignInClient=configureGoogleAuth();
            Intent signInIntent =googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    void navigateToRegister() {
        register.setOnClickListener(view ->
                Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_singUpFragment));
    }


    void setLoginAsGust() {
        loginAsGust.setOnClickListener(view -> {
                    stateOfBottomNav.isGust(true);
                    loginPresenter.loginAsGust();

                }
        );
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
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.
                    getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                progressDialog.setMessage(getString(R.string.loading_login));
                progressDialog.show();
                if (account != null) {
                    loginPresenter.loginWithGoogle(account.getIdToken());
                }
            } catch (ApiException ignored) {
            }
        }
    }

    @Override
    public void succeedLogin() {
        progressDialog.dismiss();
        moveToHome();
    }


    @Override
    public void failureLogin(String message) {
        if(googleSignInClient!=null){
            googleSignInClient.signOut();
        }
        progressDialog.dismiss();
           if (!message.isEmpty()) {
               Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();

       }
    }

    @Override
    public void loginAsGust() {
        moveToHome();
    }

    @Override
    public void resultValidate(Validation validation) {
        if(validation.isValid()){
            progressDialog.setMessage(getString(R.string.loading_login));
            progressDialog.show();
            emailP.setErrorEnabled(false);
            passwordP.setErrorEnabled(false);
        }else {
            printError(validation);
        }

    }

    void moveToHome() {
      Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeF);
    }
}
