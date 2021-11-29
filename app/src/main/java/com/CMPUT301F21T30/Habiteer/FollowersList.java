package com.CMPUT301F21T30.Habiteer;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Current user's follower list and when clicked
 * displays the users in the follower list in recycler view
 */
public class FollowersList extends AppCompatActivity {


    private RecyclerView followerRecycler;

    private ArrayList<User> followerList;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_list);

        ActionBar ab = getSupportActionBar();
        //enable back button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        this.setTitle("Followers");
        followerRecycler = findViewById(R.id.follower_recyclerView);
        currentUser = Session.getInstance().getUser();
        followerList = currentUser.getFollowingList();

        Log.d("TAG 566", String.valueOf(followerList.size()));

        recyclerSetUp();

    }

    /**
     * Displays the list of follower of current users in the recycler view
     */
    public void recyclerSetUp(){

        FollowerAdapter followerAdapter = new FollowerAdapter(followerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        followerRecycler.setAdapter(followerAdapter);
        followerRecycler.setLayoutManager(layoutManager);


        followerRecycler.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(followerRecycler.getContext(), layoutManager.getOrientation());
        followerRecycler.addItemDecoration(divider);
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