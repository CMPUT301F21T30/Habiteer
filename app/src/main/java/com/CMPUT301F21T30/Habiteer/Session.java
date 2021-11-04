package com.CMPUT301F21T30.Habiteer;

import static android.content.ContentValues.TAG;

import static androidx.core.content.ContextCompat.startActivity;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Session {
    /**
     * This class holds the current user's User object, which contains the user's
     * habits, following and followers, and blocked accounts.
     */
    private FirebaseFirestore db;
    private User user;
    private static Session instance = null;
    private DocumentReference document;

    /**
     * Singleton Session constructor
     * 
     * @param email, which is the document name in firestore
     */
    private Session(String email, Context context) {
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                user.setEmail(docRef.getId()); // document does not set email to User, so we set manually
                System.out.println("Session user: " + user.getEmail() + ", " + user.getHabitList()); // TODO
                Toast.makeText(context, "You have been logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent); // if logged in, go to the main activity
            }
        });
    }

    public static Session getInstance(String email, Context context) {
        if (instance == null) {
            instance = new Session(email, context);
        }
        return instance;
    }

    public static Session getInstance() {
        return instance;
    }

    public User getUser() {
        return this.user;
    }

    public void storeHabits(List<Habit> habitList) {
        user.setHabitList(new ArrayList<Habit>(habitList));
        System.out.println("Habit name: " + user.getHabitList().get(0).getHabitName());

        /* Store onto Firebase */
        db.collection("Users").document(user.getEmail()).update("habitList", user.getHabitList())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}
