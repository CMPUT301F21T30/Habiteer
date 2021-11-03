package com.CMPUT301F21T30.Habiteer.ui.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;

import java.util.ArrayList;
import java.util.List;

public class FollowingList extends AppCompatActivity implements FollowingListAdapter.OnUserListener {

    RecyclerView followingRecycler;
    ArrayList<User> followingArrayList;
    ArrayAdapter<User> followingAdapter;

    User givenUser;

    //User userFollowingList;


    String name;
    List<String> dataFollowing = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_list);

        followingRecycler = findViewById(R.id.list_following);

        this.setTitle("Following");

        //JUST FOR TESTING

        User user1 = new User();
        user1.setEmail("123@gm.ca");

        User user2 = new User();
        user2.setEmail("ww@tgg");

        User user3 = new User();
        user3.setEmail("ggg@ff.ca");


        ArrayList<User> following = new ArrayList<>();

        following.add(user2);
        following.add(user3);

        user1.setFollowingList(following);


        givenUser = new User("123@gm.ca"); //need to change the argument



        followingArrayList = new ArrayList<>();
        followingArrayList = givenUser.getFollowingList();

        /**
        followingAdapter = new ArrayAdapter<>(this, R.layout.content_following, followingArrayList);

        followingListView.setAdapter(followingAdapter); */


        for (int i = 0; i<followingArrayList.size(); i++){
            User userFollowingList = followingArrayList.get(i);
            name =  userFollowingList.getEmail();
            dataFollowing.add(name);
        }


        dataFollowing.add("12e@gm.ca");
        dataFollowing.add("new@cz");

        FollowingListAdapter followingAdapter = new FollowingListAdapter(this, dataFollowing);

        followingRecycler.setAdapter(followingAdapter);
        followingRecycler.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    public void onUserSelected(String userName) {
        Intent intent = new Intent(FollowingList.this, View_others_habits.class);
        intent.putExtra("User", userName);
        startActivity(intent);


    }
}