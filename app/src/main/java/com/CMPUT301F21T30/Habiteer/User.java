package com.CMPUT301F21T30.Habiteer;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habitEvents.Event;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String email;
    private ArrayList<String> habitIdList;
    private ArrayList<Event> eventList;
    private ArrayList<Habit> publicHabits;

    private ArrayList<String> sentRequestsList;
    private ArrayList<String> followRequestsList;
    private ArrayList<String> followerList;
    private ArrayList<String> followingList;
//    private ArrayList<User> blockList;

    public User() { }

    public User(String email) {
        this.email = email;
        this.habitIdList = new ArrayList<>();
        this.eventList = new ArrayList<>();
        this.followerList = new ArrayList<>();
        this.followingList = new ArrayList<>();
//        this.blockList = new ArrayList<>();
        this.publicHabits = new ArrayList<>();
        this.sentRequestsList = new ArrayList<>();
        this.followRequestsList = new ArrayList<>();

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

    public ArrayList<String> getFollowerList() {
        return followerList;
    }

    public ArrayList<String> getFollowingList() {
        return followingList;
    }

//    public ArrayList<User> getBlockList() {
//        return blockList;
//    }

    public void setFollowingList(ArrayList<String> followingList) {
        this.followingList = followingList;
    }

    public void setFollowerList(ArrayList<String> followerList) {
        this.followerList = followerList;
    }

    public void setSentRequestsList(ArrayList<String> sentRequestsList) {
        this.sentRequestsList = sentRequestsList;
    }

    public void setFollowRequestsList(ArrayList<String> followRequestsList) {
        this.followRequestsList = followRequestsList;
    }

    public ArrayList<String> getSentRequestsList() {
        return sentRequestsList;
    }

    public ArrayList<String> getFollowRequestsList() {
        return followRequestsList;
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
        System.out.println("BRK 11");
        this.email = email;
    }

    /*public ArrayList<Habit> getPublicHabits(){//Problem here
        HashMap<String,Habit> userHabits = (HashMap<String, Habit>) Session.getInstance().getOthersHabits(this).clone(); // TODO Get this User's habits from firebase based on habit ID and clone into publicHabits
        Iterator iterator = userHabits.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry habitPair = (Map.Entry)iterator.next();
            Habit habit = (Habit) habitPair.getValue();
            if (habit.getPublic()) {
                publicHabits.add(habit);
            }
        }
        return publicHabits;
    }*/




}
