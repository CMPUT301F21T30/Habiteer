package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import java.util.Date;

public class Event {
    private String eventName;
    private String makeDate;
    //    private Integer progress;
    Event() {} // no-argument constructor for firebase
    Event(String eventName) {
        this.eventName = eventName;
    }
    public Event(String eventName,String makeDate) {
        this.eventName = eventName;
        this.makeDate = makeDate;
    }
    public String getEventName() {
        return eventName;
    }

    public String getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
