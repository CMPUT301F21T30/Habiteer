package com.CMPUT301F21T30.Habiteer;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String email;
    private ArrayList<Habit> habitList;
    //private ArrayList<HabitEvent> habitEvents;
    private ArrayList<User> followerList;
    private ArrayList<User> followingList;
    private ArrayList<User> blockList;
    public User() { }

    public User(String email) {
        this.email = email;
        this.habitList = new ArrayList<>();
//        this.habitEvents = new ArrayList<>();
        this.followerList = new ArrayList<>();
        this.followingList = new ArrayList<>();
        this.blockList = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Habit> getHabitList() {
        return habitList;
    }
    public void addHabit(Habit habit) {this.habitList.add(habit);}
    public void deleteHabit(Habit habit) {this.habitList.remove(habit);}



    public ArrayList<User> getFollowerList() {
        return followerList;
    }

    public ArrayList<User> getFollowingList() {
        return this.followingList;

    }

    public ArrayList<User> getBlockList() {
        return blockList;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setHabitList(ArrayList<Habit> habitList) {
        this.habitList = habitList;
    }

    public void setFollowingList(ArrayList<User> followingList) {
        this.followingList = followingList;
    }




}
