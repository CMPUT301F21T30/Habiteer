package com.CMPUT301F21T30.Habiteer.ui.habit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

/**
 * This class stores Habit details and allows creation of new Habits.
 * It holds a habit's name, start date, end date, reason, and ID
 */
public class Habit {
    private String habitName;
    private Date startDate;
    private Date endDate;
    List<MaterialDayPicker.Weekday> weekdayList;
    private String reason;
    private Integer progress;
    private String id;
    private ArrayList<String> eventIdList;

    /**
     * No-argument constructor, used only for firebase.
     */
    Habit() {} // no-argument constructor for firebase

    Habit(String habitName) {
        this.habitName = habitName;
    }

    /**
     * Public constructor for a habit.
     *
     * @param habitName the name of the habit
     * @param startDate the date to start the habit
     * @param endDate the date to the habit ends
     * @param reason the reason for doing the habit
     */
    public Habit(String habitName,Date startDate,Date endDate,List<MaterialDayPicker.Weekday> weekdayList,String reason) {
        this.habitName = habitName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekdayList = weekdayList;
        this.reason = reason;
        this.eventIdList = new ArrayList<>();

    }

    // Getters and setter methods, self-explanatory

    public String getHabitName() {
        return habitName;
    }

    public List<MaterialDayPicker.Weekday> getWeekdayList() {
        return weekdayList;
    }

    public Date getStartDate() {
        return startDate;
    }


    public Date getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public Integer getProgress() {
        return progress;
    }

    public String getId() { return id; }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setWeekdayList(List<MaterialDayPicker.Weekday> weekdayList) {this.weekdayList = weekdayList;}

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void setId(String id) { this.id = id; }

    public ArrayList<String> getEventIdList() {
        return eventIdList;
    }

    public void setEventIdList(ArrayList<String> eventIdList) {
        this.eventIdList = eventIdList;
    }


}

