package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class to add new habit event
 */
public class AddHabitEventActivity extends AddEditHabitEvent_BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    // To initialize variables
    


    String date = "";
    String habitID;
    TextInputLayout eventDateView;
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
        // Set up the special toolbar with the save button for this activity
        Toolbar toolbar = findViewById(R.id.bar_add_habit_event);
        setSupportActionBar(toolbar);
        this.setTitle("Add habit event");

        // To pass habit index
        habitID = getIntent().getStringExtra("habitID");

        // date of event
        date = getIntent().getStringExtra("eventDate");
        eventDateView = findViewById(R.id.textInput_habitEventDate);

        // To set event date
        eventDateView.getEditText().setText(date);

        getPermissions();
        // This toast confirms correct date is being passed
        //Toast.makeText(AddHabitEventActivity.this, "Date passed: " + date + ", Habit id: " + habitID, Toast.LENGTH_SHORT).show();

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
                location.setVisibility(View.INVISIBLE);
            }
        });
        handlePhotograph();




    }


    /**
     * To get and inflate options menu
     * @param menu
     * @return menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate options menu
        getMenuInflater().inflate(R.menu.add_habit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     *  To implement saving a habit event after
     *  all necessary fields are obtained from user
     * @param item
     * @return bool
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.button_addHabit: // save button
                addEvent();
                return true;
        }
        return false;
    }

    /**
     * To set the map layout
     * and record location marker
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        activeMarker = map.addMarker(new MarkerOptions()
                .position(new LatLng(defaultLocation.latitude,
                        defaultLocation.longitude))
                .draggable(true));
        map.setOnMarkerDragListener(this);
        updateLocationUI();
        getDeviceLocation();
    }

    // To get location and camera permissions from user device
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

    // To update the location layout
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

    /**
     * Function to obtain the device's current location and record the change in location
     * and update location to where the user drags the location marker to
     */
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
                                        activeMarker.setPosition(new LatLng(curLocation.getLatitude(),
                                                curLocation.getLongitude()));
                                        finalLocation = new GeoPoint(curLocation.getLatitude(), curLocation.getLongitude());
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
     * function to get event name and event comment
     * and update the database with these new details
     */
    public void addEvent() {
        eventNameInput = findViewById(R.id.event_name_input);
        eventName = eventNameInput.getText().toString();

        eventCommentInput = findViewById(R.id.event_comment_input);
        eventComment = eventCommentInput.getText().toString();

        Habit currentHabit = Session.getInstance().getHabitHashMap().get(habitID);

        Event event = new Event(eventName, eventComment, date,getUploadUri(), habitID);

        if (finalLocation != null) {
            event.setLongitude(finalLocation.getLongitude());
            event.setLatitude(finalLocation.getLatitude());
        }

        Session.getInstance().addEvent(event, habitID);
        finish();

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    /**
     * update location after user has finished dragging the marker
     * @param marker
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng temp = activeMarker.getPosition();
        finalLocation = new GeoPoint(temp.latitude, temp.longitude);
    }

}
