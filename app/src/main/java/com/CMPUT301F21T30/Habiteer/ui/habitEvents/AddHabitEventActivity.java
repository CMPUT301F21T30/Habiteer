package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.habit.ViewHabitActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;

public class AddHabitEventActivity extends AppCompatActivity {
    private CalendarView calendar;
    String date = "";
    String TAG = "Sample";
    int habitIndex;
    String indexString;
    TextView eventDate;
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
        eventDate = findViewById(R.id.textInput_habitEventDate);

        eventDate.setText("Event date: " + date);

        Toast.makeText(AddHabitEventActivity.this, "Date passed: " + date + ", Habit index: " + habitIndex, Toast.LENGTH_SHORT).show();

    }

    public void addEvent(View view) {
        eventNameInput = findViewById(R.id.event_name_input);
        eventName = eventNameInput.getText().toString();

        eventCommentInput = findViewById(R.id.event_comment_input);
        eventComment = eventCommentInput.getText().toString();

        Event event = new Event(eventName, eventComment);
        Session.getInstance().addEvent(event);
        finish();
    }
}
