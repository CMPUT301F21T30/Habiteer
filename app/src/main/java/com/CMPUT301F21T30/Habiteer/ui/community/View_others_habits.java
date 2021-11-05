package com.CMPUT301F21T30.Habiteer.ui.community;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;
import java.util.List;

public class View_others_habits extends AppCompatActivity {

    RecyclerView habitRecyclerView;
    ArrayList<Habit> habitList;

    Integer progress;
    String habitName;

    List<String> dataHabitTitles = new ArrayList<>();
    List<Integer> dataProgress = new ArrayList<>();

    User mainUser; //the user who is following others

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_others_habits);

        /** habitRecyclerView = findViewById(R.id.list_habits_others);

        mainUser = (User) getIntent().getSerializableExtra("User"); //User object from Intent

        this.setTitle(mainUser.getEmail());
        habitList = mainUser.getHabitList();


        //checks if a habit is public
        // and then only adds the habit name and progress indicator to the respective lists

        for (int j = 0; j<habitList.size(); j++){
            if (habitList.get(j).getPublicHabit()){
                habitName = habitList.get(j).getHabitName(); //get the name of the habits for the user
                progress = habitList.get(j).getProgress(); //get the progress for each habit of the user

                dataHabitTitles.add(habitName);
                dataProgress.add(progress);
            }

        }


        //displays the habit name and progress.
        ViewOthersHabitsAdapter habitAdapter = new ViewOthersHabitsAdapter(this, dataHabitTitles, dataProgress);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        habitRecyclerView.setAdapter(habitAdapter);
        habitRecyclerView.setLayoutManager(layoutManager);

        // animation and borders
        habitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(habitRecyclerView.getContext(), layoutManager.getOrientation());
        habitRecyclerView.addItemDecoration(divider);

         */
    }

}