package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import java.util.Date;


/**
 * To get and set new event name, event date and event comment
 * and create a new event object
 */
public class Event {
    private String eventName;
    private String eventComment;
    private String makeDate; // TODO store this as a Date object, not a string
    private String id;
    //    private Integer progress;
    Event() {} // no-argument constructor for firebase
    Event(String eventName) {
        this.eventName = eventName;
    }
    public Event(String eventName,String eventComment,String eventDate) {
        this.eventName = eventName;
        this.eventComment = eventComment;
        this.makeDate = eventDate;
    }
    public String getEventName() {
        return eventName;
    }

    public String getMakeDate() {
        return makeDate;
    }

    public String getEventComment() {return eventComment;}

    public String getId() { return id; }

    public void setMakeDate(String makeDate) {this.makeDate = makeDate; }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventComment(String comment) {this.eventComment=comment;}

    public void setId(String id) { this.id = id; }

}
