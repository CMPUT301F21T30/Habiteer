package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;


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
    private String imageUri;


    private GeoPoint location;
    //    private Integer progress;
    Event() {} // no-argument constructor for firebase
    Event(String eventName) {
        this.eventName = eventName;
    }
    public Event(String eventName,String eventComment,String eventDate, String imageUri, String habitId) {
        this.eventName = eventName;
        this.eventComment = eventComment;
        this.makeDate = eventDate;
        this.habitId = habitId;
        this.imageUri = imageUri;
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

    public GeoPoint getLocation() { return location; }

    public void setMakeDate(String makeDate) {this.makeDate = makeDate; }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventComment(String comment) {this.eventComment=comment;}

    public void setHabitId(String comment) {this.habitId=habitId;}

    public void setId(String id) { this.id = id; }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }
    public void setLocation(GeoPoint location) {this.location = location;}

}
