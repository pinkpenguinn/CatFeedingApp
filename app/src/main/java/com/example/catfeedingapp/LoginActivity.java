package com.example.catfeedingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailID, password;
    Button signInButton;
    TextView linkToSignUp;
    FirebaseAuth firebaseAuthorization;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuthorization = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.LoginPassword);
        signInButton = findViewById(R.id.buttonSignIn);
        linkToSignUp = findViewById(R.id.linkToSignUp);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = firebaseAuthorization.getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Please log in", Toast.LENGTH_SHORT).show();
                }

            }
        };

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailAddress = emailID.getText().toString();
                String loginPassword = password.getText().toString();

                if (emailAddress.isEmpty()) {
                    emailID.setError("Please enter your Email Address");
                    emailID.requestFocus();
                } else if (loginPassword.isEmpty()) {
                    password.setError("Please enter a password");
                    password.requestFocus();
                } else if (!emailAddress.isEmpty() && !loginPassword.isEmpty()) {
                    firebaseAuthorization.signInWithEmailAndPassword(emailAddress, loginPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Error, Please login again", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent goToHomePage = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(goToHomePage);
                            }
                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }


        });

        linkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpPage = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(signUpPage);
            }
        });

    }

        protected void onStart() {
            super.onStart();
            firebaseAuthorization.addAuthStateListener(mAuthStateListener);
        }


    }

