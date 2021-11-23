package com.CMPUT301F21T30.Habiteer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;

public class ViewOtherHabits extends AppCompatActivity {

    private RecyclerView habitRecyclerView;
    private ArrayList<Habit> habitList;
    int userIndex;

    TextView followers;
    TextView following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_habits);

        habitRecyclerView = findViewById(R.id.list_habits_others);

        followers = findViewById(R.id.followers_count);
        following = findViewById(R.id.following_count_text);



        User user1 = new User("a@ual.ca");

        this.setTitle(user1.getEmail());

        habitList = new ArrayList<>();

        // get the user index from the intent
        Bundle bundle = getIntent().getExtras();
        userIndex = bundle.getInt("userIndex");


        // get current user at that index
        //User clickedUser = Session.getInstance().getUser().getFollowingList().get(userIndex);

        //int followers_count = clickedUser.getFollowerList().size();
        //int following_count = clickedUser.getFollowingList().size();


        //display followers and following count
        //followers.setText(String.valueOf(followers_count));
        //following.setText(String.valueOf(following_count));


        //this.setTitle(clickedUser.getEmail());

        // get habit info
        //habitList = Session.getInstance(clickedUser.getEmail(), this).getHabitList();



        //TODO: get public habit

        /**TEST**/
        Habit habit1 = new Habit();
        habit1.setHabitName("reading");
        habit1.setProgress(90);

        Habit habit2 = new Habit();
        habit2.setHabitName("Dancing");
        habit2.setProgress(50);

        habitList.add(habit1);
        habitList.add(habit2);



        //Display the content in the recycler view
        ViewOtherHabitsAdapter habitAdapter = new ViewOtherHabitsAdapter(habitList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        habitRecyclerView.setAdapter(habitAdapter);
        habitRecyclerView.setLayoutManager(layoutManager);

        habitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(habitRecyclerView.getContext(), layoutManager.getOrientation());
        habitRecyclerView.addItemDecoration(divider);


    }
}