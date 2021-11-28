package com.CMPUT301F21T30.Habiteer.ui.Follow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;

public class FollowUserActivity extends AppCompatActivity {
    TextView followersCount, followingCount, bio;
    Button followBtn;
    RecyclerView habitsRecycler;
    private ArrayList<Habit> habitsList;
    private User selectedUser;
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



        habitsList = new ArrayList<Habit>();

        //Get the user
        Bundle bundle = getIntent().getExtras();
        selectedUser = (User) bundle.getSerializable("UserObj");

        //TODO: Get the clicked user from firestore
        //User user =

        //this.setTitle(user.getEmail());

        //Getting current user
        User currentUser = Session.getInstance().getUser();

        //Checking if current user is already following the other user
        /*if(currentUser.getFollowingList().contains(user)){
            following = Boolean.TRUE;
        }
        else{
            following = Boolean.FALSE;
        }*/


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