package com.CMPUT301F21T30.Habiteer;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Session {
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
                System.out.println("Session user: " + user.getEmail() + ", " + user.getHabitList()); // TODO remove
                Toast.makeText(context, "You have been logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); // remove login activity and start main activity
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
        if (instance == null) {
            throw new IllegalArgumentException("Session not instantiated! Instantiate using getInstance(String email, Context context) before calling this.");
        }
        return instance;

    }

    public User getUser() {
        return this.user;
    }

    public void storeHabits(List<Habit> habitList) {
        user.setHabitList(new ArrayList<Habit>(habitList));
//        System.out.println("Habit name: " + user.getHabitList().get(0).getHabitName());

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
    public void addHabit(Habit habit) {user.addHabit(habit);}
    public void deleteHabit(Habit habit) {user.deleteHabit(habit);}
}
