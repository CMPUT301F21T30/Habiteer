package com.CMPUT301F21T30.Habiteer.ui.habit;

import java.io.Serializable;
import java.util.Date;

public class Habit implements Serializable {
    private String habitName;
    private Date startDate;
    private Date endDate;
     private String reason;
    private Integer progress;

    private Boolean publicHabit;


    Habit() {
        this.habitName = "Untitled";
    }

    public Habit(String habitName) {
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
    public Integer getProgress(){
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }


    public void setPublicHabit(Boolean publicHabit) {
        this.publicHabit = publicHabit;
    }

    public Boolean getPublicHabit() {
        return publicHabit;
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
