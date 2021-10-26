package com.example.streambuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    private EditText email, password, name;
    private Button login;
    private TextView addnewAccount, passwordRecovery;
    FirebaseUser user;
    private FirebaseAuth authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        addnewAccount = findViewById(R.id.needs_new_account);
        passwordRecovery = findViewById(R.id.forgetp);
        authenticator = FirebaseAuth.getInstance();
        login = findViewById(R.id.login_button);

        if (authenticator != null){
            user = authenticator.getCurrentUser();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(message).matches()){
                    email.setError("Email invalid");
                    email.setFocusable(true);
                } else {
                    loginUser(message, pass);
                }
            }
        });

        addnewAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, Registration.class);
                startActivity(intent);
            }
        });

        passwordRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecovery();
            }
        });
    }

    private void showRecovery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover your password");
        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailET = new EditText(this);
        emailET.setText("mail");
        emailET.setMinEms(16);
        emailET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailET);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String message = emailET.getText().toString().trim();
                Recover(message);
            }
        });
        builder.setNegativeButton("Canel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void Recover(String message){
        authenticator.sendPasswordResetEmail(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginScreen.this, "Sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginScreen.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginScreen.this, "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginUser(String emailer, String password){

        authenticator.signInWithEmailAndPassword(emailer, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser newser = authenticator.getCurrentUser();

                    if (task.getResult().getAdditionalUserInfo().isNewUser()){
                        String email = newser.getEmail();
                        String userID = newser.getUid();
                    }
                    Toast.makeText(LoginScreen.this, "User Registered Successfully " + newser.getEmail(), Toast.LENGTH_LONG).show();
                    Intent main = new Intent (LoginScreen.this, Dashboard.class);
                    main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(main);
                    finish();
                } else {
                    Toast.makeText(LoginScreen.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginScreen.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}