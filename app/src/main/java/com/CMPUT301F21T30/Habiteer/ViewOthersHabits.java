package com.CMPUT301F21T30.Habiteer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.CMPUT301F21T30.Habiteer.ui.main.ViewOthersHabitsFragment;

public class ViewOthersHabits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_others_habits_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ViewOthersHabitsFragment.newInstance())
                    .commitNow();
        }
    }
}