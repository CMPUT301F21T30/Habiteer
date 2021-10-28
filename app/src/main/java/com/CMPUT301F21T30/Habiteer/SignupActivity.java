package com.CMPUT301F21T30.Habiteer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

                }




                /**
                 * Registering the user in Firebase and checking if the user has been registered successfully
                 */
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(com.CMPUT301F21T30.Habiteer.SignupActivity.this, "You have been registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //if registered, the user goes to the main activity
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
            }
        });
    }

}
