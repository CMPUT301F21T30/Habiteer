package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habit.ViewHabitActivity;
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
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditHabitEventActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener{
    // To initialize variables

    String newTitle;
    String newComment;
    Button saveButton;
    Button deleteButton;
    TextView title;
    TextView comment;
    TextInputLayout date;
    Button location;
    private GoogleMap map;
    private LinearLayout layout;
    String manifestPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    boolean locPermission;
    boolean camPermission;
    Location curLocation;
    private int DEFAULT_ZOOM = 15;
    private GeoPoint finalLocation;
    Marker activeMarker;
    LatLng defaultLocation = new LatLng(0.0,0.0);
    Event event;
    private FusedLocationProviderClient fusedLocationProviderClient;


    /**
     * To set habit event layout, get intent and set event date
     * Creates an on click listener for add button
     * and creates an on click listener for location
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_habit_event_activity);

        getPermissions();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // date of event
        event = (Event) getIntent().getSerializableExtra("event");
        title = findViewById(R.id.event_name_input);
        title.setText(event.getEventName());
        date = findViewById(R.id.textInput_habitEventDate);
        date.getEditText().setText(event.getMakeDate());
        comment = findViewById(R.id.event_comment_input);
        comment.setText(event.getEventComment());
        layout = findViewById(R.id.layoutLocation);
        location = findViewById(R.id.button_addHabitEventLocation);
        if (event.getLatitude() == null)
        {
            location.setVisibility(View.VISIBLE);
        }
        else
        {
            getPermissions();
            location.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(EditHabitEventActivity.this);
        }
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermissions();
                location.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(EditHabitEventActivity.this);

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
                if (finalLocation != null) {
                    event.setLatitude(finalLocation.getLatitude());
                    event.setLongitude(finalLocation.getLongitude());
                }

                if (newTitle.isEmpty()) {
                    title.setError("Please enter a title.");
//                    message = "Required Field is empty";
//                    Toast.makeText(EditHabitEventActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    Session.getInstance().updateEvent(event);
                    finish();
                }

            }
        });

        // To connect to the delete button and set an on click listener
        deleteButton = findViewById(R.id.deleteHabitEvent);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "DELETE START");
                Session.getInstance().deleteEvent(event);
                Log.d(TAG, "DELETE MID");
                finish();
                //Intent intent = new Intent(EditHabitEventActivity.this, ViewHabitActivity.class);
                //startActivity(intent);
            }
        });
    }

    /**
     * to set the map layout to what the user had previously saved
     * and set an onclick listener on map
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        if (event.getLatitude() != null)
        {
            activeMarker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(event.getLatitude(), event.getLongitude()))
                    .draggable(true));
        }
        else
        {
            activeMarker = map.addMarker(new MarkerOptions()
                    .position(defaultLocation)
                    .draggable(true));
        }
        updateLocationUI();
        getDeviceLocation();
        map.setOnMarkerDragListener(this);

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    /**
     * To save the updated location
     * @param marker
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng temp = activeMarker.getPosition();
        finalLocation = new GeoPoint(temp.latitude, temp.longitude);
    }

    // Ask user for location and camera permissions
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

    // To get the device's current location and set the map layout accordingly
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
                                        if (event.getLatitude() != null)
                                        {
                                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                                    new LatLng(event.getLatitude(),
                                                            event.getLongitude()), DEFAULT_ZOOM));
                                            activeMarker.setPosition(new LatLng(event.getLatitude(), event.getLongitude()));
                                            finalLocation = new GeoPoint(event.getLatitude(), event.getLongitude());
                                        }
                                        else
                                        {
                                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                                    new LatLng(curLocation.getLatitude(),
                                                            curLocation.getLongitude()), DEFAULT_ZOOM));
                                            activeMarker.setPosition(new LatLng(curLocation.getLatitude(), curLocation.getLongitude()));
                                            finalLocation = new GeoPoint(curLocation.getLatitude(), curLocation.getLongitude());
                                        }

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
}

