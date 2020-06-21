package com.example.catfeedingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    EditText emailID, password;
    Button signUpButton;
    TextView linkToSignIn;
    FirebaseAuth firebaseAuthorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuthorization = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        signUpButton = findViewById(R.id.buttonSignUp);
        linkToSignIn = findViewById(R.id.linkToSignIn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailID.getText().toString();  // gets user inputted data on email address field
                String loginPassword = password.getText().toString(); // gets user inputted data on password field

                if(emailAddress.isEmpty()) {
                    emailID.setError("Please enter your Email Address");  // if field is empty, error msg is displayed
                    emailID.requestFocus();  // attention is directed towards the email field
                }

                else if (loginPassword.isEmpty()) {
                    password.setError("Please enter a password");  // if field is empty, error msg is displayed
                    password.requestFocus();  // attention directed towards the password field
                }

                else if (!emailAddress.isEmpty() && !loginPassword.isEmpty() ) {
                    firebaseAuthorization.createUserWithEmailAndPassword(emailAddress, loginPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));

                            }

                            else {
                                Toast.makeText(MainActivity.this, "Sign Up Unsuccessful, Please try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                    );
                }

                else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        // click link to go to log in page
        linkToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });


    }
}
