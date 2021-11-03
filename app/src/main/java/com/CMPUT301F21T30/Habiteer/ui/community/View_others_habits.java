package com.CMPUT301F21T30.Habiteer.ui.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habit.HabitAdapter;

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

        habitRecyclerView = findViewById(R.id.list_habits_others);

        mainUser = (User) getIntent().getSerializableExtra("User"); //User object from Intent

        this.setTitle(mainUser.getEmail());
        habitList = mainUser.getHabitList();


        for (int j = 0; j<habitList.size(); j++){
            //TODO: check if the habit is public or private using an if condition


                habitName = habitList.get(j).getHabitName(); //get the name of the habits for the user
                progress = habitList.get(j).getProgress(); //get the progress for each habit of the user

                dataHabitTitles.add(habitName);
                dataProgress.add(progress);


        }


        //displays the habit name and progress.
        ViewOthersHabitsAdapter habitAdapter = new ViewOthersHabitsAdapter(this, dataHabitTitles, dataProgress);
        habitRecyclerView.setAdapter(habitAdapter);
        habitRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

}