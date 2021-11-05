package com.CMPUT301F21T30.Habiteer.ui.habit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitActivity;

//import ca.antonious.materialdaypicker.MaterialDayPicker;

public class ViewHabitActivity extends AppCompatActivity {
    TextView habitNameHeading, habitName, datesHeading, dates, daysHeading, reasonHeading, reason, progressHeading;
    Button addHabitEvent, delete, edit;
    ProgressBar progress;
    Switch privateSwitch;
    //MaterialDayPicker days;


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
        //days = findViewById(R.id.days);
        addHabitEvent = findViewById(R.id.addHabitEvent);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        progress = findViewById(R.id.progress);
        privateSwitch = findViewById(R.id.privateSwitch);



        // get the habit index from the intent
        Bundle bundle = getIntent().getExtras();
        int habitIndex = bundle.getInt("habitIndex");



        // get current habit at that index
        Habit currentHabit = Session.getInstance().getUser().getHabitList().get(habitIndex);



        // get habit info
        String habitname = currentHabit.getHabitName();
        String startdate = currentHabit.getStartDate().toString();
        String enddate = currentHabit.getEndDate().toString();
        String reason_ = currentHabit.getReason();

        // formatting the date properly
        String unwanted = "00:00:00 MDT ";
        String replacement = "";
        String finalStartDate = startdate.replaceAll(unwanted, replacement).substring(4);
        String finalEndDate = enddate.replaceAll(unwanted, replacement).substring(4);


        // displaying the habit info
        habitName.setText(habitname);
        dates.setText(finalStartDate + " - " + finalEndDate);
        reason.setText(reason_);

        //List<MaterialDayPicker.Weekday> daysSelected = Lists.newArrayList(MaterialDayPicker.Weekday.TUESDAY, MaterialDayPicker.Weekday.FRIDAY);
        //days.setSelectedDays(daysSelected);

        /**
         * Checking if the user made the habit private
         */
        privateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){

                    //privateHabits.add(new Habit());//adding habit to the list of private habits

                }

            }
        });

        /**
         * If the user clicks on the add habit event button
         */
        addHabitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), AddHabitEvent.class)); //the user goes to the addHabitEvent activity

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
                startActivity(new Intent(getApplicationContext(), AddEditHabitActivity.class)); //the user goes to the EditHabit activity
                int habitIndex = new AddEditHabitActivity().getIntent().getExtras().getInt("habitIndex");
            }
        });
    }
}