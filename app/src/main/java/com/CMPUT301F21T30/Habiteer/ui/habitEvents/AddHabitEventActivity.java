package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Class to add new habit event
 */
public class AddHabitEventActivity extends AppCompatActivity {
    // To initialize variables
    String date = "";
    String habitID;
    TextView eventDateView;
    TextInputEditText eventNameInput;
    String eventName;

    TextInputEditText eventCommentInput;
    String eventComment;

    Button addButton;

    /**
     * To set habit event layout, get intent and set event date
     * Creates an on click listener for add button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event_activity);

        // To pass habit index
        habitID = getIntent().getStringExtra("habitID");

        // date of event
        date = getIntent().getStringExtra("eventDate");
        eventDateView = findViewById(R.id.textInput_habitEventDate);

        // To set event date
        eventDateView.setText("Event date: " + date);

        // This toast confirms correct date is being passed
        Toast.makeText(AddHabitEventActivity.this, "Date passed: " + date + ", Habit id: " + habitID, Toast.LENGTH_SHORT).show();

        // To connect to the add button and set an on click listener
        addButton = findViewById(R.id.button_addHabitEvent);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent(view);
            }
        });

    }

    /**
     * function to get event name and event comment
     * and update the database with these new details
     * @param view
     */
    public void addEvent(View view) {
        eventNameInput = findViewById(R.id.event_name_input);
        eventName = eventNameInput.getText().toString();

        eventCommentInput = findViewById(R.id.event_comment_input);
        eventComment = eventCommentInput.getText().toString();

        Habit currentHabit = Session.getInstance().getHabitHashMap().get(habitID);

        Event event = new Event(eventName, eventComment,date, habitID);

        Session.getInstance().addEvent(event, habitID);
        finish();
    }
}
