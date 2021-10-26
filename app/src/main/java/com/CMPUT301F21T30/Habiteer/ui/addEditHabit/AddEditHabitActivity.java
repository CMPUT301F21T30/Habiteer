package com.CMPUT301F21T30.Habiteer.ui.addEditHabit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.CMPUT301F21T30.Habiteer.R;

public class AddEditHabitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_habit_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AddEditHabitFragment.newInstance())
                    .commitNow();
        }
    }
}