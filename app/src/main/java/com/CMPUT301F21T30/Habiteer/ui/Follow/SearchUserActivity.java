package com.CMPUT301F21T30.Habiteer.ui.Follow;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.ui.habit.HabitAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class SearchUserActivity extends AppCompatActivity {
    private EditText searchView;
    private RecyclerView searchRecycler;
    private List<User> searchList;
    private SearchUserAdapter searchUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_search_user);
        recyclerSetup();

        searchView = findViewById(R.id.searchView);

        String searchUser = searchView.getText().toString().trim();
        doSearch(searchUser);



    }
    private void recyclerSetup() {
        // set up the recycler view
        searchUserAdapter = new SearchUserAdapter(searchList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        searchRecycler.setLayoutManager(layoutManager);
        searchRecycler.setItemAnimator(new DefaultItemAnimator());
        searchRecycler.setAdapter(searchUserAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(searchRecycler.getContext(), layoutManager.getOrientation());
        searchRecycler.addItemDecoration(divider);
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
                        User user = document.toObject(User.class); // turn the search result into a user class
                        searchList.add(user); // add it to the search list
                        searchUserAdapter.notifyDataSetChanged();

                        //Log.d(TAG, document.getId() + " => " + document.getData());
                    }

                } else {
                    //Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
            });




    }
;}
