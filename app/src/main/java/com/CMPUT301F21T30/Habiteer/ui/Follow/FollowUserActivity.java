package com.CMPUT301F21T30.Habiteer.ui.Follow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;

public class FollowUserActivity extends AppCompatActivity {
    TextView followersCount, followingCount, bio;
    Button followBtn;
    RecyclerView habitsRecycler;
    private ArrayList<Habit> habitsList;
    private int userIndex;
    private FollowUserHabitAdapter followUserHabitAdapter;
    private Boolean following;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_user);
        
        habitsRecycler = findViewById(R.id.habitsRecycler);
        followBtn = findViewById(R.id.followBtn);
        followersCount = findViewById(R.id.followersCount);
        followingCount = findViewById(R.id.followingCount);
        bio = findViewById(R.id.bio);
        following = Boolean.TRUE;


        habitsList = new ArrayList<Habit>();

        //Get the user index
        Bundle bundle = getIntent().getExtras();
        userIndex = bundle.getInt("userIndex");

        //TODO: Get the clicked user from firestore
        //User user =

        //this.setTitle(user.getEmail());

        //String followers_count = String.valueOf(user.getFollowerList().size());
        //String following_count = String.valueOf(user.getFollowingList().size());
        //TODO: Get bio for the user

        //TODO: Get only public habits
        //ArrayList habits_list = user.getHabitIdList().;

        //displayInfo(followers_count, following_count, bio_text, habits_list);

        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!following){
                    //Send a follow request
                    followBtn.setText("Requested");
                }
                else{
                    following = Boolean.FALSE;
                }
            }
        });
        
    }
    private void displayInfo(String followers_count, String following_count, String bio_text, ArrayList habits_list){
        followersCount.setText(followers_count);
        followingCount.setText(following_count);
        bio.setText(bio_text);

        if (following){
            followBtn.setText("Following");
            followUserHabitAdapter = new FollowUserHabitAdapter(habitsList);
            habitsRecycler.setAdapter(followUserHabitAdapter);
            habitsRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            followBtn.setText("Follow");
        }
}}