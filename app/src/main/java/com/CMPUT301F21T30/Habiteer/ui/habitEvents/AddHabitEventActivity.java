package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.CMPUT301F21T30.Habiteer.R;

public class AddHabitEventActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event_activity);
        // Set up the special toolbar for this activity
        Toolbar toolbar = findViewById(R.id.bar_add_habit_event);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        //enable back button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);


    }
}
