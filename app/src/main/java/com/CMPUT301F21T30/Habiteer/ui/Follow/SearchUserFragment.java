package com.CMPUT301F21T30.Habiteer.ui.Follow;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SearchUserFragment extends Fragment {
    private SearchView searchView;
    private RecyclerView searchRecycler;
    private List<User> searchList;
    private SearchUserAdapter searchUserAdapter;
    private SearchUserViewModel mViewModel;
    private Button searchBtn;

    public static SearchUserFragment newInstance() {
        return new SearchUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_user_fragment, container, false);

        super.onCreate(savedInstanceState);


//        setContentView(R.layout.activity_search_user);
        searchList = new ArrayList<User>();
        searchRecycler = root.findViewById(R.id.searchList);
        searchList.add(new User("test@tester.ca")); //TODO remove this test code
        System.out.println(searchList);
        recyclerSetup();
        searchView = root.findViewById(R.id.searchView);
        searchBtn = root.findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchUser = searchView.getQuery().toString().trim();
                doSearch(searchUser);
                System.out.println(searchUser);
                System.out.println("I am here");


            }
        });



        return root;



    }
    private void recyclerSetup() {
        // set up the recycler view
        searchUserAdapter = new SearchUserAdapter(searchList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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
                    System.out.println("Success");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println(document);
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


}