package com.CMPUT301F21T30.Habiteer.ui.Follow;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchUserFragment extends Fragment {
    private SearchView searchView;
    private RecyclerView searchRecycler;
    private List<User> searchList;
    private SearchUserAdapter searchUserAdapter;
    private SearchUserViewModel mViewModel;
    private Button searchBtn;
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
        searchRecycler = root.findViewById(R.id.searchList);
        searchRecycler.setAdapter(searchUserAdapter);
        searchList.add(new User("test@tester.ca")); //TODO remove this test code
        System.out.println(searchList);

        searchView = root.findViewById(R.id.searchView);
        //searchBtn = root.findViewById(R.id.searchBtn);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                    getAllUsers();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText.trim())){
                    doSearch(newText);
                }
                else{
                    getAllUsers();
                }
                return false;
            }
        });


        return root;




    }

    private void getAllUsers() {
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
    }

    private void doSearch(final String s){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                searchList.clear();
                System.out.println("Working");

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
        });
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


