package com.example.foodplanner.ui.auth.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.User;
import com.example.foodplanner.data.models.Validation;
import com.example.foodplanner.ui.auth.validation.AuthInputValidatorImpl;
import com.example.foodplanner.ui.auth.validation.AuthenticationImpl;
import com.example.foodplanner.utils.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SingUpFragment extends Fragment {
    TextInputEditText emailText, passwordText, repeatedPasswordText;
    TextInputLayout emailText_P, passwordText_P, repeatedPasswordText_P;
    AppCompatButton register;
    TextView login;
    SingUpPresenter registerPresenter;
    FirebaseAuth firebaseAuth;


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
    }

    void inti(View view) {
        emailText = view.findViewById(R.id.email_value_register);
        passwordText = view.findViewById(R.id.password_value_register);
        repeatedPasswordText = view.findViewById(R.id.repeated_password_value_register);
        register = view.findViewById(R.id.register_b_register);
        login = view.findViewById(R.id.go_to_login);

        emailText_P=view.findViewById(R.id.email_p_register);
        passwordText_P=view.findViewById(R.id.password_p_register);
        repeatedPasswordText_P=view.findViewById(R.id.confirm_password_p_register);
        firebaseAuth = FirebaseAuth.getInstance();
        registerPresenter = new SingUpPresenter(new AuthenticationImpl(new AuthInputValidatorImpl()));

    }

    void registerAction() {
        register.setOnClickListener(view -> validateData());
    }

    User getData() {
        return new User(emailText.getText().toString(),
                passwordText.getText().toString()
                , repeatedPasswordText.getText().toString()
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
                        firebaseAuth.createUserWithEmailAndPassword(
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
        Navigation.findNavController(requireView()).navigate(R.id.action_singUpFragment_to_homeF);

    }

    void backToLogin() {
        login.setOnClickListener(view -> Navigation.findNavController(view).popBackStack());
    }

}