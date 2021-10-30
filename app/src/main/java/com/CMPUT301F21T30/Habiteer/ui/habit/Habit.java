package com.CMPUT301F21T30.Habiteer.ui.habit;

import java.util.Date;

public class Habit {
    private String habitName;
//    private Date startDate;
//    private Date endDate;
//    private String reason;
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
}
