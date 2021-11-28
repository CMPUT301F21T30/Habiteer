package com.CMPUT301F21T30.Habiteer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.CMPUT301F21T30.Habiteer.ui.habitEvents.Event;

import org.junit.jupiter.api.Test;

public class TestEvent {

    private Event mockEvent(){
        return new Event("event1", "new event", "11/12/2021");
    }

    @Test
    public void testGetEventName(){
        Event event = mockEvent();
        event.setEventName("new Event");
        assertEquals("new Event", event.getEventName());
    }

    @Test
    public void testGetMakeDare(){
        Event event = mockEvent();
        event.setMakeDate("11/12/2021");
        assertEquals("11/12/2021", event.getMakeDate());
    }

    @Test
    public void testGetEventComment(){
        Event event = mockEvent();
        event.setEventComment("new");
        assertEquals("new", event.getEventComment());
    }
}
