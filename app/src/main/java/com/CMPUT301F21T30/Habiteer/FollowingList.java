package com.CMPUT301F21T30.Habiteer;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Displays the current user's (user who is logged in) following list in the recycler view
 */
public class FollowingList extends AppCompatActivity  {

    private RecyclerView followingRecycler;

    private ArrayList<User> followingList;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_list);

        ActionBar ab = getSupportActionBar();
        //enable back button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        this.setTitle("Following");
        followingRecycler = findViewById(R.id.following_recyclerView);
        currentUser = Session.getInstance().getUser();
        followingList = currentUser.getFollowingList();

        recyclerSetUp();

    }

    /**
     * Displays the list of following users in the recycler view
     */
    public void recyclerSetUp(){
        FollowingListAdapter followingAdapter = new FollowingListAdapter(followingList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        followingRecycler.setAdapter(followingAdapter);
        followingRecycler.setLayoutManager(layoutManager);

        followingRecycler.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(followingRecycler.getContext(), layoutManager.getOrientation());
        followingRecycler.addItemDecoration(divider);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: // back button
                finish();
                return true;
        }
        return false;
    }
}