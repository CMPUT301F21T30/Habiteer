package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;


/**
 * To get and set new event name, event date and event comment
 * and create a new event object
 */
public class Event implements Serializable {
    private String eventName;
    private String eventComment;
    private String makeDate; // TODO store this as a Date object, not a string
    private String id;
    private String habitId;
    private Double longitude;
    private Double latitude;
    //    private Integer progress;
    Event() {} // no-argument constructor for firebase
    Event(String eventName) {
        this.eventName = eventName;
    }
    public Event(String eventName,String eventComment,String eventDate, String habitId) {
        this.eventName = eventName;
        this.eventComment = eventComment;
        this.makeDate = eventDate;
        this.habitId = habitId;
        this.latitude = null;
        this.longitude = null;
    }
    public String getEventName() {
        return eventName;
    }

    public String getMakeDate() {
        return makeDate;
    }

    public String getEventComment() {return eventComment;}

    public String getHabitId() {return habitId;}

    public String getId() { return id; }

    public Double getLongitude() {return this.longitude;}

    public Double getLatitude() {return this.latitude;}

    public void setMakeDate(String makeDate) {this.makeDate = makeDate; }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventComment(String comment) {this.eventComment=comment;}

    public void setHabitId(String habitId) {this.habitId=habitId;}

    public void setId(String id) { this.id = id; }

    public void setLatitude(Double latitude) {this.latitude = latitude;}

    public void setLongitude(Double longitude) {this.longitude = longitude;}


}
