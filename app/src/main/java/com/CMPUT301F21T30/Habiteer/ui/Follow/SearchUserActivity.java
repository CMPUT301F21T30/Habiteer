package com.CMPUT301F21T30.Habiteer.ui.Follow;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.CMPUT301F21T30.Habiteer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class SearchUserActivity extends AppCompatActivity {
    EditText searchView;
    RecyclerView searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchView = findViewById(R.id.searchView);

        String searchUser = searchView.getText().toString().trim();
        doSearch(searchUser);



    }

    private void doSearch(String searchUser){

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        CollectionReference usersDocRef = db.collection("Users");
        Task<QuerySnapshot> query = usersDocRef.whereEqualTo(searchUser, true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        //Log.d(TAG, document.getId() + " => " + document.getData());
                    }

                } else {
                    //Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
            });




    }
;}
