package com.CMPUT301F21T30.Habiteer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    User user;
    String name;

    TextView bio_edit;
    AlertDialog dialog;
    EditText editText_bio;

    TextView followers;
    TextView following;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

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


        //user = Session.getInstance().getUser();
        user = new User("bushra@ual.ca");
        user.setFollowingList(f1);
        user.setFollowerList(f2);

        name = user.getEmail();


        this.setTitle(name);

        bio_edit = findViewById(R.id.edit_bio_text);

        followers = findViewById(R.id.followers_count);

        following = findViewById(R.id.following_count_text);


        int followers_count = user.getFollowerList().size();
        int following_count = user.getFollowingList().size();


        //display followers and following count
        followers.setText(String.valueOf(followers_count));
        following.setText(String.valueOf(following_count));


        String original_bio = user.getBio();

        if (original_bio.equals("")){
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


        //TODO: update the bio in database

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

        //when clicked on the following count open new activity

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(UserProfile.this, FollowingList.class);
                startActivity(myIntent);
            }
        });

    }

}