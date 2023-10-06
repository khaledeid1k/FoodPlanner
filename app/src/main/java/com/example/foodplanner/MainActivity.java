package com.example.foodplanner;

import static com.example.foodplanner.utils.Extensions.showRequestLogDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanner.ui.auth.AuthView;
import com.example.foodplanner.utils.Constants;
import com.example.foodplanner.utils.Extensions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity implements AuthView {
    NavController controller;
    BottomNavigationView bottomNavigationView;
    NavHostFragment navHostFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);

        init();
        disappearAndShowBottomNavigation();

    }

    void init(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);

        controller = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, controller);

    }

    void disappearAndShowBottomNavigation(){
        controller.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int destinationId = destination.getId();
            if (destinationId == R.id.homeF || destinationId == R.id.category ||destinationId == R.id.search ||
                    destinationId == R.id.favourite || destinationId == R.id.plan
            ) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else {
                bottomNavigationView.setVisibility(View.GONE);
            }
        });

    }


    void  controlToBottomNavigationClicks(){
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.favourite||itemId==R.id.plan) {
                 showRequestLogDialog(controller,this);
                    return false;
            }
                return NavigationUI.onNavDestinationSelected(item, controller);

        });

    }

    @Override
    public void IsLogin(boolean isLogin) {
        Log.i("MainActivitylolo", "IsLogin: "+isLogin);
        if(!isLogin) {
            NavigationUI.setupWithNavController(bottomNavigationView, controller);
            controlToBottomNavigationClicks();
            controller.popBackStack(R.id.loginFragment, false);
        }

    }
}



