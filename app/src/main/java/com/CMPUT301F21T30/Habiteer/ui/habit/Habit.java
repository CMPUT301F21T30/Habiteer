package com.CMPUT301F21T30.Habiteer.ui.habit;

import java.io.Serializable;
import java.util.Date;

public class Habit implements Serializable {
    private String habitName;
//    private Date startDate;
//    private Date endDate;
//    private String reason;
    private Integer progress;

    Habit() {
        this.habitName = "Untitled";
    }

    public Habit(String habitName) {
        this.habitName = habitName;
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

}
