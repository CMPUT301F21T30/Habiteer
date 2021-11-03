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

    String name;


    User main_user; //the user who is following others

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_others_habits);

        habitRecyclerView = findViewById(R.id.list_habits_others);


        name = getIntent().getStringExtra("User");

        this.setTitle(name);

        main_user = new User(name);


        //set the title of the activity
        //this.setTitle(main_user.getEmail());

        habitList = main_user.getHabitList();


            for (int j = 0; j<habitList.size(); j++){
                habitName = habitList.get(j).getHabitName(); //get the name of the habits for the user
                progress = habitList.get(j).getProgress(); //get the progress for each habit of the user

                dataHabitTitles.add(habitName);
                dataProgress.add(progress);

            }



        //for testing

        List<String> dataHabitTitles = new ArrayList<>();
        List<Integer> dataProgress = new ArrayList<>();


        dataHabitTitles.add("Habit 1");
        dataHabitTitles.add("habit 2");

        dataProgress.add(10);
        dataProgress.add(20);


        ViewOthersHabitsAdapter habitAdapter = new ViewOthersHabitsAdapter(this, dataHabitTitles, dataProgress);

        habitRecyclerView.setAdapter(habitAdapter);
        habitRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

}