package com.CMPUT301F21T30.Habiteer.ui.addEditHabit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.CMPUT301F21T30.Habiteer.R;

public class AddEditHabitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_habit_activity);
        // Set up the special toolbar for this activity
        Toolbar toolbar = findViewById(R.id.bar_add_habit);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        //enable back button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        Boolean EditMode = extras.getBoolean("EditMode");
        Fragment destFrag;
        if (EditMode) {
            destFrag = EditHabitFragment.newInstance();
            this.setTitle(R.string.title_editHabit);
        }
        else {
            destFrag = AddEditHabitFragment.newInstance();
            this.setTitle(R.string.title_addHabit);
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, destFrag)
                    .commitNow();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_habit_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.button_addHabit:
                return false;
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}