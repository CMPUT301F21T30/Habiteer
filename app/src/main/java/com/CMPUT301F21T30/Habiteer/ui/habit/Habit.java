package com.CMPUT301F21T30.Habiteer.ui.habit;

import java.util.Date;

/**
 * This class stores Habit details and allows creation of new Habits.
 * It holds a habit's name, start date, end date, and reason
 */
public class Habit {
    private String habitName;
    private Date startDate;
    private Date endDate;
    private String reason;
//    private Integer progress;
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
    public Habit(String habitName,Date startDate,Date endDate,String reason) {
        this.habitName = habitName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    // Getters and setter methods, self-explanatory

    public String getHabitName() {
        return habitName;
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

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
