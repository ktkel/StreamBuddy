package com.example.streambuddy;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.streambuddy.Dashboard;
import com.example.streambuddy.LoginScreen;
import com.example.streambuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseUser currentUser;
    private FirebaseAuth authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        authenticator = FirebaseAuth.getInstance();
        if (authenticator != null){
            currentUser = authenticator.getCurrentUser();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = authenticator.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent anotherIntent = new Intent(SplashScreen.this, Dashboard.class);
                    anotherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(anotherIntent);
                    finish();
                }
            }
        }, 1000);
    }
}