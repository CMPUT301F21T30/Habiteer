package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habit.ViewHabitActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class EditHabitEventActivity extends AppCompatActivity {
    // To initialize variables
    //String date = "";
    //int habitIndex;
    //String indexString;
    //TextView eventDateView;
    TextInputEditText eventNameInput;
    String eventName;

    TextInputEditText eventCommentInput;
    String eventComment;

    Button saveButton;
    Button deleteButton;
    TextView title;
    TextView reason;
    Integer habitIndex;


    /**
     * To set habit event layout, get intent and set event date
     * Creates an on click listener for add button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_habit_event_activity);


        // date of event
        Event event = (Event) getIntent().getSerializableExtra("event");
        title = findViewById(R.id.event_name_input);
        title.setText(event.getEventName());
        reason = findViewById(R.id.event_comment_input);
        reason.setText(event.getEventComment());

        // To connect to the save button and set an on click listener
        saveButton = findViewById(R.id.saveHabitEvent);
        //saveButton.setOnClickListener();

        // To connect to the delete button and set an on click listener
        deleteButton = findViewById(R.id.deleteHabitEvent);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Habit> habits = Session.getInstance().getHabitList();
                for (int i = 0; i < habits.size(); i++)
                {
                    if (habits.get(i).getId().equals(event.getHabitId()))
                    {
                        habitIndex = i;
                    }
                }
                Session.getInstance().deleteEvent(event, habitIndex);
                finish();
//                Intent intent = new Intent(EditHabitEventActivity.this, ViewHabitActivity.class);
//                startActivity(intent);
            }
        });

    }
}

