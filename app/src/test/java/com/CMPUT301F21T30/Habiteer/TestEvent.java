package com.CMPUT301F21T30.Habiteer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.CMPUT301F21T30.Habiteer.ui.habitEvents.Event;

import org.junit.jupiter.api.Test;


/**JUnit testing for the Event class
 * Tests getters and setters for event class
 */
public class TestEvent {

    //Creates a mock event
    private Event mockEvent(){
        return new Event("event1", "new event", "11/12/2021","","");
    }

    //tests getEventName method and also setEventName method
    @Test
    public void testGetEventName(){
        Event event = mockEvent();
        event.setEventName("new Event");
        assertEquals("new Event", event.getEventName());
    }

    //tests getMakeDate and setMakeDate method
    @Test
    public void testGetMakeDate(){
        Event event = mockEvent();
        event.setMakeDate("11/12/2021");
        assertEquals("11/12/2021", event.getMakeDate());
    }

    //tests getEventComment and setEventComment method
    @Test
    public void testGetEventComment(){
        Event event = mockEvent();
        event.setEventComment("new");
        assertEquals("new", event.getEventComment());
    }
}
