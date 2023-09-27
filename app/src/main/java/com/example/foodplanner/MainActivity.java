package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen.Companion.*;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
    }
}