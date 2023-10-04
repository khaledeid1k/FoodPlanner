package com.example.foodplanner.ui.auth.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.AuthInputValidatorImpl;
import com.example.foodplanner.ui.auth.validation.AuthenticationImpl;
import com.example.foodplanner.utils.Constants;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    TextInputEditText emailText, passwordText;
    TextInputLayout emailP, passwordP;
    TextView register,loginAsGust;
    AppCompatButton login;
    LoginPresenter loginPresenter;
    FirebaseAuth firebaseAuth;


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
        checkIfUserLoginBefore(view);
        loginAction();
        navigateToRegister();
        setLoginAsGust();
    }

    void inti(View view) {
        emailText = view.findViewById(R.id.email_value_login);
        passwordText = view.findViewById(R.id.password_value_login);
        emailP = view.findViewById(R.id.email_p);
        passwordP = view.findViewById(R.id.password_p);
        login = view.findViewById(R.id.login_b);
        register = view.findViewById(R.id.go_to_register);
        loginAsGust = view.findViewById(R.id.login_as_gust);
        firebaseAuth = FirebaseAuth.getInstance();
        loginPresenter = new LoginPresenter(new AuthenticationImpl(new AuthInputValidatorImpl()));
    }

    void loginAction() {
        login.setOnClickListener(view -> validateData());
    }

    User getData() {
        return new User(emailText.getText().toString(),
                passwordText.getText().toString());
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

                                emailText.getText().toString(),
                                passwordText.getText().toString()).addOnCompleteListener(
                                requireActivity(), task -> {
                                    if (task.isSuccessful()) {
                                        navigateToHome();

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

    void checkIfUserLoginBefore(View view) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            navigateToHome();
        }
    }

    void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeF);
    }

    void navigateToRegister() {
        register.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_singUpFragment));
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            navigateToHome();
        }
    }

    void setLoginAsGust(){
        loginAsGust.setOnClickListener(view -> navigateToHome());
    }
}
