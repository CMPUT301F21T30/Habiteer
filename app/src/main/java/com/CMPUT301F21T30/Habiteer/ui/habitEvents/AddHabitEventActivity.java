package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;

import android.os.Environment;
import android.provider.MediaStore;
import static android.content.ContentValues.TAG;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to add new habit event
 */
public class AddHabitEventActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    // To initialize variables
    
    private int habitIndex;
    private ImageView selectedImage;
    public static final int CAM_PERM_CODE = 101;
    public static final int CAM_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    private String uploadUri;
    private Uri UriOfImage;
    private String currentPhotoPath;
    private StorageReference storageReference; //to store the image in firebase storage
    String date = "";
    String habitID;
    TextView eventDateView;
    TextInputEditText eventNameInput;
    String eventName;
    TextInputEditText eventCommentInput;
    String eventComment;
    Button addButton;
    Button location;
    private GoogleMap map;
    private LinearLayout layout;
    String manifestPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Boolean locPermission;
    Boolean camPermission;
    Location curLocation;
    private int DEFAULT_ZOOM = 15;
    GeoPoint finalLocation;
    Marker activeMarker;
    LatLng defaultLocation = new LatLng(0.0,0.0);
    private FusedLocationProviderClient fusedLocationProviderClient;

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


        storageReference = FirebaseStorage.getInstance().getReference(); //initialize the storage reference



        getPermissions();
        // This toast confirms correct date is being passed
        Toast.makeText(AddHabitEventActivity.this, "Date passed: " + date + ", Habit id: " + habitID, Toast.LENGTH_SHORT).show();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        location = findViewById(R.id.button_addHabitEventLocation);
        layout = findViewById(R.id.layoutLocation);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermissions();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(AddHabitEventActivity.this);
                layout.setVisibility(View.VISIBLE);
            }
        });

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
                Log.d("tag 2", "onActivityResult: Gallery Image Uri" + imageFileName);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        updateLocationUI();
        getDeviceLocation();
    }

    public void getPermissions()
    {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionLocation = ContextCompat.checkSelfPermission(this, manifestPermission);
        List<String> listPermissions = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissions.add(manifestPermission);
        }
        else
        {
            locPermission = true;
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissions.add(Manifest.permission.CAMERA);
        }
        else
        {
            camPermission = true;
        }
        if (!listPermissions.isEmpty())
        {
            ActivityCompat.requestPermissions(this, listPermissions.toArray(new String[listPermissions.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }

    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locPermission) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                curLocation = null;
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */

        try {
            if (locPermission) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this,
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                if (task.isSuccessful()) {
                                    // Set the map's camera position to the current location of the device.
                                    curLocation = task.getResult();
                                    if (curLocation != null) {
                                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                                new LatLng(curLocation.getLatitude(),
                                                        curLocation.getLongitude()), DEFAULT_ZOOM));
                                        activeMarker = map.addMarker(new MarkerOptions()
                                                .position(new LatLng(curLocation.getLatitude(),
                                                        curLocation.getLongitude()))
                                                .draggable(true));
                                        finalLocation = new GeoPoint(curLocation.getLatitude(),
                                                curLocation.getLongitude());

                                    }
                                } else {
                                    Log.d(TAG, "Current location is null. Using defaults.");
                                    Log.e(TAG, "Exception: %s", task.getException());
                                    map.moveCamera(CameraUpdateFactory
                                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                                    map.getUiSettings().setMyLocationButtonEnabled(false);
                                }
                            }
                        });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
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

        Habit currentHabit = Session.getInstance().getHabitHashMap().get(habitID);

        Event event = new Event(eventName, eventComment, date, uploadUri, currentHabit.getId());
        Session.getInstance().addEvent(event, habitIndex);
        Event event = new Event(eventName, eventComment,date, habitID);

        if (finalLocation != null)
            event.setLocation(finalLocation);

        Session.getInstance().addEvent(event, habitID);
        finish();

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng temp = activeMarker.getPosition();
        finalLocation = new GeoPoint(temp.latitude, temp.longitude);
    }
}
