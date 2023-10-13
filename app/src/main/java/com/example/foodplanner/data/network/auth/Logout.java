package com.example.foodplanner.data.network.auth;

import com.example.foodplanner.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

public class Logout implements ILogOut {
    @Override
    public void logOut() {
        Constants.UserId="";
        FirebaseAuth.getInstance().signOut();

    }
}
