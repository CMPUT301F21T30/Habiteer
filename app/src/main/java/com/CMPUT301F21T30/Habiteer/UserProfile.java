package com.CMPUT301F21T30.Habiteer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;

/**
 * class to display user profile
 * user can add a bio and edit a bio
 * displays all the habits of the user
 */
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
        setContentView(R.layout.activity_user_profile);

        bio_edit = findViewById(R.id.edit_bio_text);
        followers = findViewById(R.id.followers_count);
        following = findViewById(R.id.following_count_text);
        habitListRecycler = findViewById(R.id.list_habits);

        ActionBar ab = getSupportActionBar();
        //enable back button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        User user1 = new User("a@ual.ca");
        User user2 = new User("a22@ual.ca");
        User user3 = new User("b@ual.ca");
        User user4 = new User("b22@ual.ca");

        ArrayList<User> f1 = new ArrayList<>();
        f1.add(user1);
        f1.add(user2);
        f1.add(user4);

        ArrayList<User> f2 = new ArrayList<>();
        f2.add(user3);


        //gets the current logged in user
        user = Session.getInstance().getUser();

        user.setFollowingList(f1);
        user.setFollowerList(f2);
        //habitList = user.getHabitIdList()

        name = user.getEmail();

        this.setTitle(name);


        //displays the following and follower count of the user
        int followers_count = user.getFollowerList().size();
        int following_count = user.getFollowingList().size();


        //display followers and following count
        followers.setText(String.valueOf(followers_count));
        following.setText(String.valueOf(following_count));


        String original_bio = user.getBio();

        if (original_bio == ""){
            bio_edit.setText("Add a bio");
        }
        else {
            bio_edit.setText(original_bio);
        }


        //edit the bio of the user

        dialog = new AlertDialog.Builder(this).create();
        editText_bio = new EditText(this);

        dialog.setTitle("Edit the bio");
        dialog.setView(editText_bio);


        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save Bio", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bio_edit.setText(editText_bio.getText());
                String bio= editText_bio.getText().toString();
                Session.getInstance().storeBio(Session.getInstance().getUser(), bio);
            }
        });
        bio_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_bio.setText(bio_edit.getText());
                dialog.show();
            }
        });

        //when clicked on the following count, a open new activity is opened

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent followingIntent = new Intent(UserProfile.this, FollowingList.class);
                startActivity(followingIntent);
            }
        });

        //when clicked on the followers count, a new activity is opened
        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent followersIntent = new Intent(UserProfile.this, FollowersList.class);
                startActivity(followersIntent);
            }
        });

    }

    /**
    //Display the content in the recycler view
    ViewOtherHabitsAdapter habitAdapter = new ViewOtherHabitsAdapter(habitList);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    habitListRecycler.setAdapter(habitAdapter);
    habitListRecycler.setLayoutManager(layoutManager);

    habitListRecycler.setItemAnimator(new DefaultItemAnimator());
    DividerItemDecoration divider = new DividerItemDecoration(habitRecyclerView.getContext(), layoutManager.getOrientation());
    habitListRecycler.addItemDecoration(divider);

     **/

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