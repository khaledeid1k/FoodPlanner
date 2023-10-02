package com.example.foodplanner.utils;

import android.view.View;

import androidx.navigation.Navigation;

public class Extensions {
   public static  void  closeFragment(View view){
       Navigation.findNavController(view).popBackStack();

   }
}
