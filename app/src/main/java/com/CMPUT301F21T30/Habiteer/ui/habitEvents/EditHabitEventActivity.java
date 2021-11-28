package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class EditHabitEventActivity extends AddEditHabitEvent_BaseActivity {
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


        // load in the image, if available
        ImageView selectedImage = findViewById(R.id.event_image);
        if (event.getImageUri() == null){
            //Log.d("tag", "entered the if condition");
            selectedImage.setImageResource(R.drawable.picture);
        }
        else{
            Uri uriSelectedImage = Uri.parse(event.getImageUri());
            Picasso.get().load(uriSelectedImage).into(selectedImage);
        }

        handlePhotograph();



        // To connect to the save button and set an on click listener

        saveButton = findViewById(R.id.saveHabitEvent);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newTitle = title.getText().toString();
                newComment = comment.getText().toString();
                event.setEventName(newTitle);
                event.setEventComment(newComment);
                event.setImageUri(getUploadUri());

                if ( newTitle.isEmpty() || newComment.isEmpty() ) {

                    message = "Required Field is empty";
                    Toast.makeText(EditHabitEventActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    Session.getInstance().updateEvent(event);
                }

                finish();
            }
        });
        //saveButton.setOnClickListener();

        // To connect to the delete button and set an on click listener
        deleteButton = findViewById(R.id.deleteHabitEvent);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.getInstance().deleteEvent(event);
                finish();

            }
        });

    }





}

