package com.CMPUT301F21T30.Habiteer.ui.habit;

import java.io.Serializable;
import java.util.Date;

public class  Habit implements Serializable {
    private String habitName;
    private Date startDate;
    private Date endDate;
    private String reason;
    private Integer progress;
    private Boolean publicHabit;

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

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Boolean getPublicHabit() {
        return publicHabit;
    }

    public void setPublicHabit(Boolean publicHabit) {
        this.publicHabit = publicHabit;
    }
}

