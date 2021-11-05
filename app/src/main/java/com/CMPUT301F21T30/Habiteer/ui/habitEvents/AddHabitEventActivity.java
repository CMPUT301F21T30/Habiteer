package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddHabitEventActivity extends AppCompatActivity {
    private CalendarView calendar;
    String date = "";
    String TAG = "Sample";
    int habitIndex;
    String indexString;
    TextView eventDateView;
    TextInputEditText eventNameInput;
    String eventName;

    TextInputEditText eventCommentInput;
    String eventComment;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event_activity);

        indexString = getIntent().getStringExtra("habitIndex");
        habitIndex = Integer.parseInt(indexString);
        // date of event
        date = getIntent().getStringExtra("eventDate");
        eventDateView = findViewById(R.id.textInput_habitEventDate);

        eventDateView.setText("Event date: " + date);

        Toast.makeText(AddHabitEventActivity.this, "Date passed: " + date + ", Habit index: " + habitIndex, Toast.LENGTH_SHORT).show();

    }

    public void addEvent(View view) {
        eventNameInput = findViewById(R.id.event_name_input);
        eventName = eventNameInput.getText().toString();

        eventCommentInput = findViewById(R.id.event_comment_input);
        eventComment = eventCommentInput.getText().toString();


        Event event = new Event(eventName, eventComment,date);
        Session.getInstance().addEvent(event);
        finish();
    }
}
