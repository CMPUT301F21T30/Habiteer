package com.CMPUT301F21T30.Habiteer.ui.Follow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;

/**
 * This activity allows the user to follow another user after performing the search.
 * It displays the number of followers, number of following, bio of the other user and
 * also the public habits of the user if the current user is already following them.
 */
public class FollowUserActivity extends AppCompatActivity {
    TextView followersCount, followingCount, bio;
    Button followBtn;
    RecyclerView habitsRecycler;
    private ArrayList<Habit> habitsList;
    private ArrayList<User> requestedList;
    private User selectedUser, currentUser;
    private FollowUserHabitAdapter followUserHabitAdapter;
    private boolean following;
    private boolean requested;


    /**
     * This method creates the FollowUser Activity which displays all the details of the user
     * and has a button to follow/unfollow the other user
     * @param savedInstanceState
     */
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
        requestedList = new ArrayList<User>();

        currentUser = Session.getInstance().getUser();


        ActionBar ab = getSupportActionBar();
        //enable back button
        //assert ab != null;
        //ab.setDisplayHomeAsUpEnabled(true);


        //Get the user
        Bundle bundle = getIntent().getExtras();
        selectedUser = (User) bundle.getSerializable("UserObj");

        //Set title of the page
        this.setTitle(selectedUser.getEmail());


        //Getting current user
        User currentUser = Session.getInstance().getUser();

        //Checking if current user is already following the other user
        if(currentUser.getFollowingList().contains(selectedUser)){
            following = true;
        }
        else{
            following = false;
        }

        //Getting number of followers and following
        String followers_count = String.valueOf(selectedUser.getFollowerList().size());
        String following_count = String.valueOf(selectedUser.getFollowingList().size());

        //Getting the bio for the user
        //String bio_text = selectedUser.getBio();
        String bio_text = "Sample Bio";//TODO: Remove test bio and uncomment the upper one when user profile is merged to main


        //TODO: Get only public habits
        //habitsList = selectedUser.getPublicHabits();
        //System.out.println(habitsList);

        displayInfo(followers_count, following_count, bio_text, habitsList);


        requested = currentUser.getSentRequestsList().contains(selectedUser.getEmail());
        if (requested) {
            followBtn.setText("Requested");
        }
        followBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * This method allows the user to send a follow request to the other user
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (!following||!requested){
                    followBtn.setText("Requested");
                    requestedList.add(currentUser);
                    //selectedUser.setRequestedList(requestedList);
                    Session.getInstance().addSentRequest(selectedUser.getEmail());
                }
                else {
                    following = false;
                }
            }
        });

    }

    /**
     * This method sets the details of the the other user including followers count, following count,
     * bio and public habits, if the user is already following the other user
     * @param followers_count
     * @param following_count
     * @param bio_text
     * @param habits_list
     */
    private void displayInfo(String followers_count, String following_count, String bio_text, ArrayList<Habit> habits_list){
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