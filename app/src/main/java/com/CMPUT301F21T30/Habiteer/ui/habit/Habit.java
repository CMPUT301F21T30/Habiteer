package com.CMPUT301F21T30.Habiteer.ui.habit;

import java.util.Date;

public class Habit {
    private String habitName;
    private Date startDate;
    private Date endDate;
    private String reason;
//    private Integer progress;

    Habit() {
        this.habitName = "Untitled";
    }

    Habit(String habitName) {
        this.habitName = habitName;
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
    

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
