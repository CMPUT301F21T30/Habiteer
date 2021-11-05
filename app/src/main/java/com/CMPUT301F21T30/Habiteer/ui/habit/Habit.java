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
    Habit() {} // no-argument constructor for firebase
    Habit(String habitName) {
        this.habitName = habitName;
    }
    public Habit(String habitName,Date startDate,Date endDate,String reason) {
        this.habitName = habitName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }
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
