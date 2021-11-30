package com.CMPUT301F21T30.Habiteer.ui.Follow;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 *This fragment shows the search page and allows the user to search for other users by typing
 * their email in the SearchView field.
 * It shows the list of all the users that start with the letters typed by the user.
 */

public class SearchUserFragment extends Fragment {
    private SearchView searchView;
    private RecyclerView searchRecycler;
    private List<User> searchList;
    private SearchUserAdapter searchUserAdapter;



    public static SearchUserFragment newInstance() {
        return new SearchUserFragment();
    }

    /**
     * This method creates the SearchView to type the email,
     * RecyclerView to display the result of te search as a list.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //inflate
        View root = inflater.inflate(R.layout.search_user_fragment, container, false);

        super.onCreate(savedInstanceState);



        searchList = new ArrayList<User>();
        searchRecycler = root.findViewById(R.id.searchList);
        recyclerSetup();
        searchView = root.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * This method takes the string as typed by the user and passes it to the doSearch method
             * @param query
             * @return false
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query.trim())){//ignore any white spaces after the string

                    doSearch(query);
                }

                return false;
            }

            /**
             * This method checks if the string has been changed and passes the resulting string
             * to the doSearch method
             * @param newText
             * @return false
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText.trim())){//ignore any white spaces after the string
                    doSearch(newText);
                }

                return false;
            }
        });



        return root;

    }

    /**
     * This method sets up the Recycler View
     */
    private void recyclerSetup() {
        searchUserAdapter = new SearchUserAdapter(getActivity(), searchList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        searchRecycler.setLayoutManager(layoutManager);
        searchRecycler.setItemAnimator(new DefaultItemAnimator());
        searchRecycler.setAdapter(searchUserAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(searchRecycler.getContext(), layoutManager.getOrientation());
        searchRecycler.addItemDecoration(divider);
    }

    /**
     *This method performs the search for the string typed by the user.
     * @param s
     */
    private void doSearch(final String s){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("Users");
        Query query = users.orderBy("email").startAt(s).endAt(s + '~');
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    List<User> results =  task.getResult().toObjects(User.class);
                    searchList.clear(); // clear the search results
                    searchList.addAll(results); // add all results to results list
                    searchUserAdapter.notifyDataSetChanged();

                }
            }
        });


    }


    }


