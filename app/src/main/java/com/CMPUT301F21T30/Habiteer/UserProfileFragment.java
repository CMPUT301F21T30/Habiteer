package com.CMPUT301F21T30.Habiteer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.databinding.FragmentUserProfile2Binding;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;

public class UserProfileFragment extends Fragment {

    private User user;
    private String name;

    private TextView followers, following;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = FragmentUserProfile2Binding.inflate(inflater, container, false).getRoot();


        followers = view.findViewById(R.id.followers_count);
        following = view.findViewById(R.id.following_count_text);


        //gets the current logged in user
        user = Session.getInstance().getUser();
        name = user.getEmail();
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("My profile - "+ name);




        //displays the following and follower count of the user
        int followers_count = user.getFollowerList().size();
        int following_count = user.getFollowingList().size();

        followers.setText(String.valueOf(followers_count));
        following.setText(String.valueOf(following_count));
        return view;

    }





}
