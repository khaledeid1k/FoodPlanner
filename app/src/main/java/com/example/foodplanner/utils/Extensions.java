package com.example.foodplanner.utils;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Random;

public class Extensions {
    public static void closeFragment(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    public static void showRequestLoginDialog(NavController controller, Context context) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle("Please log in to access this feature.");
        builder.setPositiveButton("Login", (dialog, which) -> moveToLoginScreen(controller));
        builder.setNegativeButton("Cancel", (dialog, which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public static void moveToLoginScreen(NavController controller) {
        controller.navigate(R.id.loginFragment);
    }


    public static String getRandomChar() {
        ArrayList<Character> alphabets = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabets.add(c);
        }
        Random random = new Random();
        int randomIndex = random.nextInt(alphabets.size());

        return alphabets.get(randomIndex).toString();
    }

    private static ConstraintLayout layout_error;
    private static LottieAnimationView lottieAnimationLoading;

    public static void intiStateAnimation(View view) {
        layout_error = view.findViewById(R.id.layout_network_error);
        lottieAnimationLoading = view.findViewById(R.id.lottie_animation_loading);

    }

    public static void updateUIState(boolean showLoading, boolean showError) {
        layout_error.setVisibility(showError ? View.VISIBLE : View.GONE);
        lottieAnimationLoading.setVisibility(showLoading ? View.VISIBLE : View.GONE);
    }



}
