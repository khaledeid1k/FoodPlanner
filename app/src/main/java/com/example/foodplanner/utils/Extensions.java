package com.example.foodplanner.utils;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Extensions {
    public static void closeFragment(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    public static void showRequestLogDialog(NavController controller, Context context) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle("Please log in to access this feature.");
        builder.setPositiveButton("Login", (dialog, which) -> controller.navigate(R.id.loginFragment));
        builder.setNegativeButton("Cancel", (dialog, which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

}
