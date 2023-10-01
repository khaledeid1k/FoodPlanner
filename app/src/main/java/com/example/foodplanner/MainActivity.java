package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen.Companion.*;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this,
                R.id.fragmentContainerView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return   navController.navigateUp()||  super.onSupportNavigateUp();
    }
}