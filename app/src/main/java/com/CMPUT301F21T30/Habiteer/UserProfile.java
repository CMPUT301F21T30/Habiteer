package com.CMPUT301F21T30.Habiteer;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {

    private User user;
    private String name;

    private TextView bio_edit;
    private AlertDialog dialog;
    private EditText editText_bio;

    private TextView followers, following;
    private RecyclerView habitListRecycler;
    private ArrayList<Habit> habitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);


        followers = findViewById(R.id.followers_count);
        following = findViewById(R.id.following_count_text);


        ActionBar ab = getSupportActionBar();
        //enable back button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);


        //gets the current logged in user
        user = Session.getInstance().getUser();
        name = user.getEmail();

        this.setTitle(name);


        //displays the following and follower count of the user
        int followers_count = user.getFollowerList().size();
        int following_count = user.getFollowingList().size();

        followers.setText(String.valueOf(followers_count));
        following.setText(String.valueOf(following_count));


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
