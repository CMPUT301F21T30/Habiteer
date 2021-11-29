package com.CMPUT301F21T30.Habiteer.ui.Follow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private User selectedUser;
    private FollowUserHabitAdapter followUserHabitAdapter;
    private Boolean following;


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

        //ActionBar ab = getActionBar();
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
            following = Boolean.TRUE;
        }
        else{
            following = Boolean.FALSE;
        }

        //Getting number of followers and following
        String followers_count = String.valueOf(selectedUser.getFollowerList().size());
        String following_count = String.valueOf(selectedUser.getFollowingList().size());

        //TODO: Get bio for the user
        String bio_text = "Sample Bio";


        //TODO: Get only public habits
        //habitsList = Session.getInstance().get;



        displayInfo(followers_count, following_count, bio_text, habitsList);

        followBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * This method allows the user to send a follow request to the other user
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (!following){
                    //TODO: Send a follow request
                    followBtn.setText("Requested");
                }
                else{
                    following = Boolean.FALSE;
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