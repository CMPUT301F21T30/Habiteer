package com.CMPUT301F21T30.Habiteer.ui.Follow;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchUserFragment extends Fragment {
    private EditText searchView;
    private RecyclerView searchRecycler;
    private List<User> searchList;
    private SearchUserAdapter searchUserAdapter;
    private SearchUserViewModel mViewModel;
    private Button searchBtn;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

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
        mViewModel = new ViewModelProvider(this).get(SearchUserViewModel.class);
        searchRecycler = root.findViewById(R.id.searchList);
        searchRecycler.setAdapter(searchUserAdapter);
        searchList.add(new User("test@tester.ca")); //TODO remove this test code
        System.out.println(searchList);

        searchView = root.findViewById(R.id.searchView);
        searchBtn = root.findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = searchView.getText().toString().trim();
                doSearch(email);


                /**
                 * Checking if email is entered
                 */
                if (TextUtils.isEmpty(email)){
                    searchView.setError("Email is required!");
                    return;
                }

            }
        });

        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query.trim())){
                    //System.out.println("query1");
                    //System.out.println(query);
                    doSearch(query);
                }
                else{
                    //System.out.println("query2");
                    //System.out.println(query);
                    //getAllUsers();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText.trim())){
                    doSearch(newText);
                }
                else{
                    //getAllUsers();
                }
                return false;
            }
        });*/

        //EventChangeListener();


        return root;




    }

    /*private void EventChangeListener(){
        db = FirebaseFirestore.getInstance();
        db.collection("Users").orderBy("email", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.e("Firestore Error", error.getMessage());
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        searchList.add(dc.getDocument().toObject(User.class));
                    }
                    searchUserAdapter.notifyDataSetChanged();
                }
            }
        });
    }*/

    /*private void getAllUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                searchList.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    User user = dataSnapshot1.getValue(User.class);
                    if(user.getEmail() != null && !user.getEmail().equals(firebaseUser.getEmail())){
                        searchList.add(user);
                    }
                    searchUserAdapter = new SearchUserAdapter(getActivity(), searchList);
                    //searchUserAdapter.notifyDataSetChanged();
                    searchRecycler.setAdapter(searchUserAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
    private void doSearch(final String s){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("Users");
        System.out.println("users");
        System.out.println(users);
        System.out.println(users.document());
        System.out.println(users.document().getId());
        Task<QuerySnapshot> query = users.whereEqualTo(s, true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                System.out.println(task);
                System.out.println(task.getResult());

                if (task.isSuccessful()){
                    System.out.println("Task Successful");
                    for (QueryDocumentSnapshot document : task.getResult()){
                        System.out.println("Success");
                        User user = document.toObject(User.class);

                    }
                }
            }
        });

        /*FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //Query query = firebaseDatabase.ref("Users");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        System.out.println(reference);
        System.out.println("I am here");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                searchList.clear();
                System.out.println("Working");
                System.out.println(dataSnapshot.getChildrenCount());
                //User user = dataSnapshot.getChildrenCount().getValue(User.class);

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    System.out.println("Not working");

                    User user = dataSnapshot1.getValue(User.class);


                    if (user.getEmail() != null && !user.getEmail().equals(firebaseUser.getEmail())){
                        if (user.getEmail().toLowerCase().contains(s.toLowerCase(Locale.ROOT))) {
                            searchList.add(user);

                        }
                    }
                    searchUserAdapter = new SearchUserAdapter(getActivity(), searchList);
                    searchUserAdapter.notifyDataSetChanged();
                    searchRecycler.setAdapter(searchUserAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    private void recyclerSetup() {
        // set up the recycler view
        searchUserAdapter = new SearchUserAdapter(getActivity(), searchList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        searchRecycler.setLayoutManager(layoutManager);
        searchRecycler.setItemAnimator(new DefaultItemAnimator());
        searchRecycler.setAdapter(searchUserAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(searchRecycler.getContext(), layoutManager.getOrientation());
        searchRecycler.addItemDecoration(divider);
    }





    }


