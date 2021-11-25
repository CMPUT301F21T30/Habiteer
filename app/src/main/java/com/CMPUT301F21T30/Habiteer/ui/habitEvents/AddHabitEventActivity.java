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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to add new habit event
 */
public class AddHabitEventActivity extends AppCompatActivity {
    // To initialize variables
    String date = "";
    int habitIndex;
    String indexString;
    TextView eventDateView;
    TextInputEditText eventNameInput;
    String eventName;

    TextInputEditText eventCommentInput;
    String eventComment;

    Button addButton;
    private ImageView selectedImage;
    public static final int CAM_PERM_CODE = 101;
    public static final int CAM_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    private Uri uploadUri;
    String currentPhotoPath;

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
        indexString = getIntent().getStringExtra("habitIndex");
        habitIndex = Integer.parseInt(indexString);

        // date of event
        date = getIntent().getStringExtra("eventDate");
        eventDateView = findViewById(R.id.textInput_habitEventDate);

        // To set event date
        eventDateView.setText("Event date: " + date);



        // This toast confirms correct date is being passed
        Toast.makeText(AddHabitEventActivity.this, "Date passed: " + date + ", Habit index: " + habitIndex, Toast.LENGTH_SHORT).show();

        // To connect to the add button and set an on click listener
        addButton = findViewById(R.id.button_addHabitEvent);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent(view);
            }
        });

        //To add image to
        selectedImage = findViewById(R.id.event_image);

        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOptions();
            }
        });

    }

    /**
     * click on the image to open an alert dialog to display options
     * alert dialog has options for camera, gallery and cancel
     */
    private void selectOptions() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder= new AlertDialog.Builder(AddHabitEventActivity.this);
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
                    Toast.makeText(AddHabitEventActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
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
        if(ContextCompat.checkSelfPermission(AddHabitEventActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
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
                selectedImage.setImageURI(Uri.fromFile(picFileName));
                Log.d("tag", "Absolute Url of image is" + Uri.fromFile(picFileName));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(picFileName);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                uploadUri = Session.getInstance().uploadImageToFirebase(picFileName.getName(), contentUri, habitIndex);
            }

        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){

                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + "." + getFileExt (contentUri);
                File f = new File(currentPhotoPath);
                Log.d("tag 2", "onActivityResult: Gallery Image Uri" + imageFileName);
                selectedImage.setImageURI(contentUri);

                //upload image to db
                uploadUri = Session.getInstance().uploadImageToFirebase(imageFileName, contentUri, habitIndex);
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
            Uri photoURI = FileProvider.getUriForFile(AddHabitEventActivity.this, "com.CMPUT301F21T30.android.fileprovider", photoFile);
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

        Habit currentHabit = Session.getInstance().getHabitList().get(habitIndex);

        Event event = new Event(eventName, eventComment,date, uploadUri, currentHabit.getId());


        Session.getInstance().addEvent(event, habitIndex);
        finish();
    }
}
