package com.CMPUT301F21T30.Habiteer;
/**
 * This is a class that shows the login page and allows the user
 * to log into the application.
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class  LoginActivity extends AppCompatActivity {
    TextView loginHeading, noAccount;
    EditText loginEmail, loginPassword;
    CheckBox rememberMe;
    Button loginBtn, goToSignupBtn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("BRK 1");

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
            /**
             * This checks for the email and password, and
             * authenticates the user using Firestore
             */
            public void onClick(View view) {
                System.out.println("BRK 2");
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

                findViewById(R.id.login_loadingSpinner).setVisibility(View.VISIBLE); // show loading indicator

                /**
                 * Authenticating the user using Firebase
                 */
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println("BRK 3");
                        if(task.isSuccessful()){
                            /**
                             * Get User object from Firestore
                             */
                            System.out.println("BRK 4");
                            Session session = Session.getInstance(email,getApplicationContext());

                        }else{
                            Toast.makeText(com.CMPUT301F21T30.Habiteer.LoginActivity.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });

        /**
         * If the user doesn't have an account, click on the signup button
         */
        goToSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class)); //move to the signup activity
                finish(); // close the current activity, so user can't go back
            }
        });



    }
}

