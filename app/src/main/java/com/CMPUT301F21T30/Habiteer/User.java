package com.CMPUT301F21T30.Habiteer;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habitEvents.Event;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String email;
    private ArrayList<String> habitIdList;
    private ArrayList<Event> eventList;

    private ArrayList<User> followerList;
    private ArrayList<User> followingList;
    private ArrayList<User> blockList;

    private String bio;

    public User() { }

    public User(String email) {
        this.email = email;
        this.habitIdList = new ArrayList<>();
        this.eventList = new ArrayList<>();
        this.followerList = new ArrayList<>();
        this.followingList = new ArrayList<>();
        this.blockList = new ArrayList<>();
        this.bio = "";

    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getHabitIdList() {
        return habitIdList;
    }
    public void addHabit(String habit) {this.habitIdList.add(habit);}
    public void deleteHabit(Habit habit) {this.habitIdList.remove(habit);}

    public void setHabitIdList(ArrayList<String> habitIdList) {
        this.habitIdList = habitIdList;
    }

    public void setFollowingList(ArrayList<User> followingList) {
        this.followingList = followingList;
    }

    public void setFollowerList(ArrayList<User> followerList) {
        this.followerList = followerList;
    }

    public ArrayList<User> getFollowerList() {
        return followerList;
    }

    public ArrayList<User> getFollowingList() {
        return followingList;
    }

    public ArrayList<User> getBlockList() {
        return blockList;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }


    public void addEvent(Event event) {this.eventList.add(event);}
    public void deleteEvent(Event event) {this.habitIdList.remove(event);}

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }
}
