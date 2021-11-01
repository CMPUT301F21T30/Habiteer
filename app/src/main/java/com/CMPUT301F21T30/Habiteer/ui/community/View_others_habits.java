package com.CMPUT301F21T30.Habiteer.ui.community;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habit.HabitAdapter;

import java.util.ArrayList;

public class View_others_habits extends AppCompatActivity {

    ListView habitListView;
    ArrayAdapter<Habit> habitArrayAdapter;
    ArrayList<Habit> habitList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_others_habits);


    }




}