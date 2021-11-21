package com.CMPUT301F21T30.Habiteer.ui.addEditHabit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.CMPUT301F21T30.Habiteer.R;

/**
 * This activity handles both the add and edit habit fragments, because it has the (top) action bar with the save button on it.
 * It determines which fragment to use based on the EditMode boolean passed in from the intent.
 */
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

        // get the edit mode from intent
        Bundle extras = getIntent().getExtras();
        Boolean EditMode = extras.getBoolean("EditMode");
        Fragment destFrag;
        if (EditMode) { // if editing
            destFrag = EditHabitFragment.newInstance();
            this.setTitle(R.string.title_editHabit);
        }
        else { // if adding
            destFrag = AddHabitFragment.newInstance();
            this.setTitle(R.string.title_addHabit);
        }
        if (savedInstanceState == null) { // replace the container with chosen fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, destFrag)
                    .commitNow();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate options menu
        getMenuInflater().inflate(R.menu.add_habit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.button_addHabit: // implemented in the fragments, so left false here
                return false;
            case android.R.id.home: // back button
                finish();
                return true;
        }
        return false;
    }
}