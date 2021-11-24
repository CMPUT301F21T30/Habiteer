package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    String newTitle;
    String newComment;
    String message;

    Button saveButton;
    Button deleteButton;
    TextView title;
    TextView comment;
    TextView date;
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
        date = findViewById(R.id.habitEventDate);
        date.setText("Event Date: " + event.getMakeDate());
        comment = findViewById(R.id.event_comment_input);
        comment.setText(event.getEventComment());

        // To connect to the save button and set an on click listener

        saveButton = findViewById(R.id.saveHabitEvent);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newTitle = title.getText().toString();
                newComment = comment.getText().toString();
                event.setEventName(newTitle);
                event.setEventComment(newComment);

                if ( newTitle.isEmpty() || newComment.isEmpty() ) {

                    message = "Required Field is empty";
                    Toast.makeText(EditHabitEventActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    Session.getInstance().updateEvent(event, habitIndex);
                }

//                ArrayList<Habit> habits = Session.getInstance().getHabitList();
//                for (int i = 0; i < habits.size(); i++)
//                {
//                    if (habits.get(i).getId().equals(event.getHabitId()))
//                    {
//                        habitIndex = i;
//                    }
//                }
//                Session.getInstance().updateEvent(event, habitIndex);
                finish();
            }
        });
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
                //Intent intent = new Intent(EditHabitEventActivity.this, ViewHabitActivity.class);
                //startActivity(intent);
            }
        });

    }
}

