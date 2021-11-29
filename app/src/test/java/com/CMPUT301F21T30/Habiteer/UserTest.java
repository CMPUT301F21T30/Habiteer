package com.CMPUT301F21T30.Habiteer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.CMPUT301F21T30.Habiteer.ui.habitEvents.Event;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;


/**
 * Tests getters and setters for the User class
 */
public class UserTest {

    //mock user
    private User mockUser(){
        return new User ("human@gmail.com");
    }

    //tests getters and setters for user email
    @Test
    void testGetUser(){
        User user = mockUser();
        assertEquals ("human@gmail.com", user.getEmail());
        assertTrue(user.getEmail().contains(user.getEmail()));
    }

    //tests getters and setters for habitId
    @Test
    void testGetHabitId(){
        User user = mockUser();
        ArrayList<String> habitIdList = new ArrayList<>();

        habitIdList.add("1234abc");
        user.setHabitIdList(habitIdList);
        assertEquals(1, user.getHabitIdList().size());
    }

    //tests getters and setters for event list
    @Test
    void testGetEventList(){
        User user = mockUser();
        ArrayList<Event> eventList = new ArrayList<>();
        Event event = new Event();

        eventList.add(event);
        user.setEventList(eventList);
        assertEquals(1, user.getEventList().size());
    }

    //tests getter and setter for the follower list
    @Test
    void testFollowerList(){
        User user = mockUser();
        ArrayList<User> followerList = new ArrayList<>();

        User user2 = new User("new@gmail.com");
        followerList.add(user2);
        user.setFollowerList(followerList);
        assertEquals(1, user.getFollowerList().size());

    }


    //tests getter and setter for following list
    @Test
    void testFollowingList(){
        User user = mockUser();
        ArrayList<User> followingList = new ArrayList<>();

        User user2 = new User("new@gmail.com");
        followingList.add(user2);
        user.setFollowingList(followingList);
        assertEquals(1, user.getFollowingList().size());

    }
    @Test
    void testAddEvent(){
        User user = mockUser();

        Event event = new Event();
        user.addEvent(event);
        assertEquals(1,user.getEventList().size());

    }

    //tests setter for user email
    @Test
    void testSetUser(){
        User user = new User();
        user.setEmail("new@gmail.com");
        assertTrue(user.getEmail().contains("new@gmail.com"));
    }



    //tests setter for habitId list
    @Test
    void testSetHabitId(){
        User user = mockUser();
        ArrayList<String> habitIdList = new ArrayList<>();

        habitIdList.add("1234abc");
        user.setHabitIdList(habitIdList);
        assertTrue(user.getHabitIdList().contains("1234abc"));
    }

}
