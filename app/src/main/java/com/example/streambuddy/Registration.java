package com.example.streambuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



import java.util.HashMap;

public class Registration extends AppCompatActivity {

    private EditText email, password, name;
    private Button register;
    private TextView liveAccount;
    private FirebaseAuth authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        email = findViewById(R.id.register_email);
        name = findViewById(R.id.register_name);
        password = findViewById(R.id.register_password);
        register = findViewById(R.id.register_button);
        liveAccount = findViewById(R.id.homepage);
        authenticator = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = email.getText().toString().trim();
                String username = name.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(message).matches()) {
                    email.setError("Invalid Email");
                    email.setFocusable(true);
                } else {
                    authenticator.createUserWithEmailAndPassword(message,pass).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Registration.this, "Successfully registered " + task.isSuccessful(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Registration.this, Dashboard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Registration.this, "Error 48x04E", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this, "Whoopsie 89x225B", Toast.LENGTH_LONG).show();
                        }
                    });                    }
            }
        });
        liveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, LoginScreen.class);
                startActivity(intent);
            }
        });
    }
    private void registerUser(String email, final String pass, final String username){
        authenticator.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = authenticator.getCurrentUser();
                    String email = user.getEmail();
                    String userID = user.getUid();
                    Toast.makeText(Registration.this, "Successfully registered " + user.getEmail(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registration.this, Dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Registration.this, "Error 48x04E", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registration.this, "Whoopsie 89x225B", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}