package com.CMPUT301F21T30.Habiteer;
/**
 * This is a class that shows the signup page and allows the user
 * to register them for the application.
 */

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    TextView signupHeading, alreadyHaveAccount;
    EditText signupEmail, signupPassword, signupConfirmPassword;
    Button signupBtn, goToLoginBtn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        signupHeading = findViewById(R.id.signupHeading);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        signupConfirmPassword = findViewById(R.id.signupConfirmPassword);
        signupBtn = findViewById(R.id.signupBtn);
        goToLoginBtn = findViewById(R.id.goToLoginBtn);
        fAuth = FirebaseAuth.getInstance();


        signupBtn.setOnClickListener(new View.OnClickListener()  { //clicking the signup button
            @Override
            /**
             * This checks for the email, password and confirmed password and
             * registers the user to firebase
             */
            public void onClick(View view) {

                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String confirmPassword = signupConfirmPassword.getText().toString().trim();

                /**
                 * Checking if email and password is entered
                 */
                if (TextUtils.isEmpty(email)){
                    signupEmail.setError("Email is required!");
                    return;

                }
                if (TextUtils.isEmpty(password)){
                    signupPassword.setError("Password is required!");
                    return;

                }
                if (TextUtils.isEmpty(confirmPassword)){
                    signupConfirmPassword.setError(("Password is required!"));
                    return;

                }


                /**
                 * Checking if password and confirm password are same
                 */
                if (TextUtils.equals(password, confirmPassword)){

                }else{
                    signupConfirmPassword.setError(("Passwords don't match!"));
                    return;
                }
                findViewById(R.id.login_loadingSpinner).setVisibility(View.VISIBLE); // show loading indicator



                /**
                 * Registering the user in Firebase and checking if the user has been registered successfully
                 */
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        FirebaseFirestore db;

                        if(task.isSuccessful()){

                            Toast.makeText(com.CMPUT301F21T30.Habiteer.SignupActivity.this, "You have been registered", Toast.LENGTH_SHORT).show();

                            User newUser = new User(email);

                            // Add user to database
                            db = FirebaseFirestore.getInstance();
                            CollectionReference collectionReference = db.collection("Users");

                            collectionReference.document(email).set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Log.d(TAG, "Data has been added successfully!");
                                    Session session = Session.getInstance(email,getApplicationContext()); //if registered, start session and goes to the main activity
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    // These are a method which gets executed if there’s any problem
                                    Log.d(TAG, "Data could not be added!" + e.toString());
                                }
                            });

                        }else{
                            Toast.makeText(com.CMPUT301F21T30.Habiteer.SignupActivity.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

        /**
         * If the user already has an account, click on the login button
         */
        goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class)); //goes to the login activity
                finish(); // close the current activity, so user can't go back
            }
        });
    }

}
