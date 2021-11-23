package com.CMPUT301F21T30.Habiteer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FollowingList extends AppCompatActivity  {

    private RecyclerView followingRecycler;

    private ArrayList<User> followingList;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_list);

        followingRecycler = findViewById(R.id.following_recyclerView);


        /**TEST**/
        User user1 = new User("a@ual.ca");
        User user2 = new User("a22@ual.ca");
        User user3 = new User("b@ual.ca");
        User user4 = new User("b22@ual.ca");

        ArrayList<User> f1= new ArrayList<>();
        f1.add(user1);
        f1.add(user2);
        f1.add(user4);

        ArrayList<User> f2 = new ArrayList<>();
        f2.add(user3);


        //user = Session.getInstance().getUser();
        user = new User("bushra@ual.ca");
        user.setFollowingList(f1);
        user.setFollowerList(f2);

        this.setTitle("Following");


        //followingList = Session.getInstance().getUser().getFollowingList();

        followingList = user.getFollowingList();

        FollowingListAdapter followingAdapter = new FollowingListAdapter(followingList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        followingRecycler.setAdapter(followingAdapter);
        followingRecycler.setLayoutManager(layoutManager);

        followingRecycler.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(followingRecycler.getContext(), layoutManager.getOrientation());
        followingRecycler.addItemDecoration(divider);

    }
}