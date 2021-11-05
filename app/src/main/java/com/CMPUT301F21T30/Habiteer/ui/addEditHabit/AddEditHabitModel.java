package com.CMPUT301F21T30.Habiteer.ui.addEditHabit;

import androidx.lifecycle.ViewModel;

import java.util.Date;

public class AddEditHabitModel extends ViewModel {
    private Date startDate;
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}