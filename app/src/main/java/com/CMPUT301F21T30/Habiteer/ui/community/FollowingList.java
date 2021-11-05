package com.CMPUT301F21T30.Habiteer.ui.community;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This class lists the following list of a particular user
 * The user is send by intent from another activity
 */
public class FollowingList extends AppCompatActivity implements FollowingListAdapter.OnUserListener {

    RecyclerView followingRecycler;
    ArrayList<User> followingArrayList;

    String name;
    List<String> dataFollowing = new ArrayList<>();
    ArrayList<User> following = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_list);

        followingRecycler = findViewById(R.id.list_following);

        this.setTitle("Following");

        //TODO: get the user for which the following list is to be displayed using INTENT


        for (int i = 0; i<followingArrayList.size(); i++){
            User userFollowingList = followingArrayList.get(i);
            name =  userFollowingList.getEmail();
            dataFollowing.add(name);  //add the user's email in the followingArrayList to dataFollowing
        }

        //Displays the following list in recycler view
        FollowingListAdapter followingAdapter = new FollowingListAdapter(this, dataFollowing);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        followingRecycler.setAdapter(followingAdapter);
        followingRecycler.setLayoutManager(layoutManager);

        // animation and borders
        followingRecycler.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(followingRecycler.getContext(), layoutManager.getOrientation());
        followingRecycler.addItemDecoration(divider);

    }

    /**
     * when a user from the following list is clicked, a new activity is opened
     * that shows details about the user that was clicked
     * @param userName
     */

    @Override
    public void onUserSelected(String userName) {


        Session sendUser = null;
        sendUser = Session.getInstance(userName, FollowingList.this);

        sendUser.getUser();


        Intent intent = new Intent(FollowingList.this, View_others_habits.class);
        intent.putExtra("User", (Parcelable) sendUser);
        startActivity(intent);


    }
}