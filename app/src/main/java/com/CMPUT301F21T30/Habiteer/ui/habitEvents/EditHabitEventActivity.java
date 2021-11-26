package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    private ImageView selectedImage;
    private Uri uriSelectedImage;

    public static final int CAM_PERM_CODE = 101;
    public static final int CAM_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    private String uploadUri;
    private Uri UriOfImage;
    private String currentPhotoPath;

    private StorageReference storageReference; //to store the image in firebase storage


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

        selectedImage = findViewById(R.id.edit_event_image);

        //selectedImage.setImageResource(R.drawable.ic_launcher_background);
        //get the image and display it in the ImageView

        storageReference = FirebaseStorage.getInstance().getReference(); //initialize the storage reference


        if (event.getImageUri() == null){
            //Log.d("tag", "entered the if condition");
            selectedImage.setImageResource(R.drawable.picture);
        }
        else{
            uriSelectedImage = Uri.parse(event.getImageUri());
            Picasso.get().load(uriSelectedImage).into(selectedImage);
        }


        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOptions();
            }
        });


        // To connect to the save button and set an on click listener

        saveButton = findViewById(R.id.saveHabitEvent);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newTitle = title.getText().toString();
                newComment = comment.getText().toString();
                event.setEventName(newTitle);
                event.setEventComment(newComment);
                event.setImageUri(uploadUri);

                if ( newTitle.isEmpty() || newComment.isEmpty() ) {

                    message = "Required Field is empty";
                    Toast.makeText(EditHabitEventActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    Session.getInstance().updateEvent(event, habitIndex);
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

            }
        });

    }

    /**
     * click on the image to open an alert dialog to display options
     * alert dialog has options for camera, gallery and cancel
     */
    private void selectOptions() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder= new AlertDialog.Builder(EditHabitEventActivity.this);
        builder.setTitle("Add an Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")){
                    askCameraPermission();
                }
                else if (items[i].equals("Gallery")){
                    openGallery();
                }
                else if (items[i].equals("Cancel")){
                    Toast.makeText(EditHabitEventActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * asks the user for camera permission
     */
    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(EditHabitEventActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAM_PERM_CODE);
        }
        else {
            dispatchTakePictureIntent();
        }
    }

    /**
     * checks if permission is granted to the camera or not
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAM_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
            else{
                Toast.makeText(this, " Camera Permission is required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                File picFileName = new File(currentPhotoPath);

                //selectedImage.setImageURI(Uri.fromFile(picFileName));

                Log.d("tag", "Absolute Url of image is" + Uri.fromFile(picFileName));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(picFileName);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                UriOfImage = Session.getInstance().uploadImageToFirebase(picFileName.getName(), contentUri, storageReference);
                Picasso.get().load(UriOfImage).into(selectedImage);
                uploadUri = UriOfImage.toString(); //changes Uri to string
            }

        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){

                Uri contentUri = data.getData();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + "." + getFileExt (contentUri);


                selectedImage.setImageURI(contentUri);

                //upload image to db
                UriOfImage = Session.getInstance().uploadImageToFirebase(imageFileName, contentUri, storageReference);
                uploadUri = UriOfImage.toString(); //changes the Uri to string

            }

        }


    }

    /**
     * @param contentUri
     * @return the extension of the image that is selected from the gallery
     */

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(c.getType(contentUri));
    }

    /**
     * creates an image file name and stores the image in an storage directory
     * @return image
     * @throws IOException
     */

    private File createImageFile() throws IOException {
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir =  getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */

        );
        //Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    /**
     * opens the camera intent to take picture
     * stores the Uri of the image
     */

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        //create the file where the photo should go
        File photoFile = null;
        try{
            photoFile = createImageFile();

        } catch (IOException e) {
            //Error occurred while creating the file
            //e.printStackTrace();
        }
        //Continue if the file was successfully created
        if (photoFile != null){
            Uri photoURI = FileProvider.getUriForFile(EditHabitEventActivity.this, "com.CMPUT301F21T30.android.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, CAM_REQUEST_CODE);
        }

    }

    /**
     * opens gallery
     */
    public void openGallery (){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST_CODE);

    }



}

