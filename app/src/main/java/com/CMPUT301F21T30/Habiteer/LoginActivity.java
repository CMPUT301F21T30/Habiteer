package com.CMPUT301F21T30.Habiteer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView loginHeading, noAccount;
    EditText loginEmail, loginPassword;
    CheckBox rememberMe;
    Button loginBtn, goToSignupBtn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginHeading = findViewById(R.id.loginHeading);
        noAccount = findViewById(R.id.noAccount);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        rememberMe = findViewById(R.id.rememberMe);
        loginBtn = findViewById(R.id.loginBtn);
        goToSignupBtn = findViewById(R.id.goToSignupBtn);

        fAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() { //clicking on the login button
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                /**
                 * Checking if email and password is entered
                 */
                if (TextUtils.isEmpty(email)){
                    loginEmail.setError("Username is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    loginPassword.setError("Password is required!");
                    return;
                }



                /**
                 * Authenticating the user using Firebase
                 */
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(com.CMPUT301F21T30.Habiteer.LoginActivity.this, "You have been logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //if logged in, go to the main activity
                        }else{
                            Toast.makeText(com.CMPUT301F21T30.Habiteer.LoginActivity.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                /**
                 * Get User object from Firestore
                 */
                Session session = Session.getInstance(email);
                User user = session.getUser();
                System.out.println("Logged in as: " + user);
            }
        });

        /**
         * If the user doesn't have an account, click on the signup button
         */
        goToSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class)); //move to the signup activity
            }
        });



    }
}

