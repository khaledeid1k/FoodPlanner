package com.example.foodplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen.Companion.*;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);

        controller = navHostFragment.getNavController();

       // NavigationUI.setupWithNavController(bottomNavigationView, controller);


        if (controller.getCurrentDestination() != null) {
            int currentFragmentId = controller.getCurrentDestination().getId();
            if (currentFragmentId == R.id.homeF ||
                    currentFragmentId == R.id.category ||
                    currentFragmentId == R.id.favourite ||
                    currentFragmentId == R.id.search ||
                    currentFragmentId == R.id.plan) {
                bottomNavigationView.setVisibility(View.VISIBLE);
                NavigationUI.setupWithNavController(bottomNavigationView, controller);
            } else {
                bottomNavigationView.setVisibility(View.GONE);
            }
        }


    }

}