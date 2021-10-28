package com.CMPUT301F21T30.Habiteer;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;

public class User {
    private String email;
    private ArrayList<Habit> habits;
//    private ArrayList<HabitEvent> habitEvents;
    private ArrayList<User> followers;
    private ArrayList<User> following;
    private ArrayList<User> blocked;
    User(String email) {
        this.email = email;
        this.habits = new ArrayList<>();
//        this.habitEvents = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.blocked = new ArrayList<>();
    }
}
