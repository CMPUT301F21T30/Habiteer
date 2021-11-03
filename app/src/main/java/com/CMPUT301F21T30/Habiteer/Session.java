package com.CMPUT301F21T30.Habiteer;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import android.app.Application;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Session extends Application {
    FirebaseFirestore db;
    private User user;


    public Session() { }

    /**
     * Initialize Session
     * @param email, which is the document name in firestore
     */
    public void init(String email) {
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                user.setEmail(docRef.getId()); // document does not set email to User, so we set manually
                System.out.println("Logged in as: " + user.getEmail());
            }
        });
    }


    public User getUser() {
        return this.user;
    }
    public void storeHabits(List<Habit> habitList) {
        user.setHabitList(new ArrayList<Habit>(habitList));
        System.out.println("Habit name: " + user.getHabitList().get(0).getHabitName());

    }
}
