package com.CMPUT301F21T30.Habiteer.ui.habit;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitActivity;
import com.CMPUT301F21T30.Habiteer.ui.habitEvents.AddHabitEventActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ca.antonious.materialdaypicker.MaterialDayPicker;

/**
 *This activity allows the user to view details of thier habits, delete the habit and takes them
 * to other activity where they can add habit event and edit habit details
 */
public class ViewHabitActivity extends AppCompatActivity {
    TextView habitNameHeading, habitName, datesHeading, dates, daysHeading, reasonHeading, reason, progressHeading, progressPer;
    Button addHabitEvent, delete, edit;
    ProgressBar progressBar;
    SwitchCompat privateSwitch;
    MaterialDayPicker dayPicker;
    Calendar calendar;
    String todayDate;
    SimpleDateFormat dateFormat;
    String habitID;

    /**
     * This method creates ViewHabit Activity which displays all the details
     * @param savedInstanceState
     */

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
        dayPicker = findViewById(R.id.ViewHabit_day_picker);
        addHabitEvent = findViewById(R.id.addHabitEvent);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        progressBar = findViewById(R.id.progress);
        privateSwitch = findViewById(R.id.privateSwitch);
        progressPer = findViewById(R.id.progress_text);

        // get the habit index from the intent
        Bundle bundle = getIntent().getExtras();
        habitID = bundle.getString("habitID");

        ActionBar ab = getSupportActionBar();
        //enable back button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);



        // get current habit with id
        Habit currentHabit = Session.getInstance().getHabitHashMap().get(habitID);

        // get habit info
        String habitname = currentHabit.getHabitName();
        SimpleDateFormat startEndFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        String startdate = startEndFormat.format(currentHabit.getStartDate());
        String enddate = startEndFormat.format(currentHabit.getEndDate());

        List<MaterialDayPicker.Weekday> weekdayList = currentHabit.getWeekdayList();
        String reason_ = currentHabit.getReason();
        double progress = currentHabit.getProgress();

        //checks currentHabit is public or private and sets the switch accordingly
        if (currentHabit.getPublic().equals(false)){
            privateSwitch.setChecked(true);
        }
        else{
            privateSwitch.setChecked(false);
        }



        // displaying the habit info
        displayHabitInfo(habitname,startdate,enddate,weekdayList,reason_, progress);


        //List<MaterialDayPicker.Weekday> daysSelected = Lists.newArrayList(MaterialDayPicker.Weekday.TUESDAY, MaterialDayPicker.Weekday.FRIDAY);
        //days.setSelectedDays(daysSelected);

        /**
         * Checking if the user made the habit private and updates the database accordingly
         */
        privateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                currentHabit.setPublic(!isChecked); // false if checked, true if not
                Session.getInstance().updateHabit(currentHabit);

            }
        });

        /**
         * If the user clicks on the add habit event button
         */
        addHabitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                todayDate = dateFormat.format(calendar.getTime());
                Log.d(TAG, "RUNNING");
                Intent intent = new Intent(getApplicationContext(), AddHabitEventActivity.class);
                intent.putExtra("habitID", String.valueOf(habitID));
                intent.putExtra("eventDate", todayDate);
                startActivity(intent); //the user goes to the addHabitEvent activity
            }
        });

        /**
         * if the user clicks on the delete habit button
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), DeleteHabit.class)); //the user goes to the DeleteHabit activity
                Session.getInstance().deleteHabit(currentHabit);

                finish();
            }
        });

        /**
         * if the user clicks on the edit habit button
         */
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEditHabitActivity.class);
                intent.putExtra("habitID",habitID); // include the index of the habit
                intent.putExtra("EditMode",true); // let the activity know to use the edit fragment
                startActivity(intent); //the user goes to the EditHabit activity
            }
        });
    }
    private void displayHabitInfo(String habitname,String finalStartDate,String finalEndDate, List<MaterialDayPicker.Weekday> weekdayList,String reason_, double progressValue) {
        habitName.setText(habitname);
        dates.setText(String.format("From: %s\nTo: %s", finalStartDate, finalEndDate));
        dayPicker.setSelectedDays(weekdayList);
        dayPicker.disableAllDays(); // make the buttons not clickable, just for viewing purposes
        reason.setText(reason_);
        progressBar.setProgress((int) progressValue);

        //progress format to two decimal places
        DecimalFormat df = new DecimalFormat("####0.00");
        String progressFormat = df.format(progressValue);


        progressPer.setText(progressFormat);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: // back button
                finish();
                return true;
        }
        return false;
    }

}