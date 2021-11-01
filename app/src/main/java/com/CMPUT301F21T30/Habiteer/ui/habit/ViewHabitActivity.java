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


        privateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    //set this habit to private


                }
                else{
                    //keep this habit public
                }
            }
        });

        addHabitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), AddHabitEvent.class)); //the user goes to the addHabitEvent activity

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), DeleteHabit.class)); //the user goes to the DeleteHabit activity
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), EditHabit.class)); //the user goes to the EditHabit activity
            }
        });
    }
}