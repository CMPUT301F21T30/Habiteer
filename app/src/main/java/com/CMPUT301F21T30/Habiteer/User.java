package com.CMPUT301F21T30.Habiteer;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String email;
    private ArrayList<String> habitIdList;

    private ArrayList<User> followerList;
    private ArrayList<User> followingList;
    private ArrayList<User> blockList;
    public User() { }

    public User(String email) {
        this.email = email;
        this.habitIdList = new ArrayList<>();
        this.followerList = new ArrayList<>();
        this.followingList = new ArrayList<>();
        this.blockList = new ArrayList<>();
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

    public ArrayList<User> getFollowerList() {
        return followerList;
    }

    public ArrayList<User> getFollowingList() {
        return followingList;
    }

    public ArrayList<User> getBlockList() {
        return blockList;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
