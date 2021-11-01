package com.CMPUT301F21T30.Habiteer;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Session {
    FirebaseFirestore db;

    /**
     * Session constructor
     * @param email, which is the document name in firestore
     */
    public Session(String email) {
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                user.setEmail(docRef.getId()); // document does not set email to User, so we set manually
                System.out.println("User email: " + user.getEmail());
            }
        });
    }
}
