package com.CMPUT301F21T30.Habiteer.ui.habit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.CMPUT301F21T30.Habiteer.MainActivity;
import com.CMPUT301F21T30.Habiteer.R;

import java.util.ArrayList;

public class ViewHabitActivity extends AppCompatActivity {
    TextView habitNameHeading, habitName, datesHeading, dates, daysHeading, reasonHeading, reason, progressHeading;
    Button days, addHabitEvent, delete, edit;
    ProgressBar progress;
    Switch privateSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        habitNameHeading = findViewById(R.id.habitNameHeading);
        habitName = findViewById(R.id.habitName);
        datesHeading = findViewById(R.id.datesHeading);
        dates = findViewById(R.id.dates);
        daysHeading = findViewById(R.id.daysHeading);
        reasonHeading = findViewById(R.id.reasonHeading);
        reason = findViewById(R.id.reason);
        progressHeading = findViewById(R.id.progressHeading);
        days = findViewById(R.id.days);
        addHabitEvent = findViewById(R.id.addHabitEvent);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        progress = findViewById(R.id.progress);
        privateSwitch = findViewById(R.id.privateSwitch);

        ArrayList<Habit> privateHabits;
        privateHabits = new ArrayList<>();

        /**
         * Checking if the user made the habit private
         */
        privateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){

                    privateHabits.add(new Habit());//adding habit to the list of private habits

                }

            }
        });

        /**
         * If the user clicks on the add habit event button
         */
        addHabitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), AddHabitEvent.class)); //the user goes to the addHabitEvent activity

            }
        });

        /**
         * if the user clicks on the delete habit button
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), DeleteHabit.class)); //the user goes to the DeleteHabit activity
            }
        });

        /**
         * if the user clicks on the edit habit button
         */
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), EditHabit.class)); //the user goes to the EditHabit activity
            }
        });
    }
}