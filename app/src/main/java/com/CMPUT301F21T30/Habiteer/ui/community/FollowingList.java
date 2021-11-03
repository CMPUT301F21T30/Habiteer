package com.CMPUT301F21T30.Habiteer.ui.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;
import java.util.List;

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

        /**JUST FOR TESTING*/

        User user1 = new User();
        user1.setEmail("123@gm.ca");

        User user2 = new User();
        user2.setEmail("john@gmail.com");

        User user3 = new User();
        user3.setEmail("pink121@gmail.com");


        following.add(user2);
        following.add(user3);

        user1.setFollowingList(following);


        followingArrayList = new ArrayList<>();
        followingArrayList = user1.getFollowingList();


        for (int i = 0; i<followingArrayList.size(); i++){
            User userFollowingList = followingArrayList.get(i);
            name =  userFollowingList.getEmail();
            dataFollowing.add(name);  //add the user's email in the followingArrayList to dataFollowing
        }

        //Displays the following list in recycler view
        FollowingListAdapter followingAdapter = new FollowingListAdapter(this, dataFollowing);
        followingRecycler.setAdapter(followingAdapter);
        followingRecycler.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void onUserSelected(String userName) {

        User sendUser;

        //TODO: get the User object sendUser from the database

        /**FOR TESTING*/

        ArrayList<Habit> habitList = new ArrayList<>();

        Habit habit1 = new Habit("Cycling");
        Habit habit2 =  new Habit("Reading");


        habit1.setProgress(50);
        habit2.setProgress(40);

        habit1.setPublicHabit(Boolean.TRUE);
        habit2.setPublicHabit(Boolean.FALSE);

        habitList.add(habit1);
        habitList.add(habit2);

        sendUser = new User(userName);
        sendUser.setHabitList(habitList);

        Intent intent = new Intent(FollowingList.this, View_others_habits.class);
        intent.putExtra("User", sendUser);
        startActivity(intent);

    }
}