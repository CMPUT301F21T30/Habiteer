package com.CMPUT301F21T30.Habiteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ca.antonious.materialdaypicker.MaterialDayPicker;

//@RunWith(AndroidJUnit4::class)
public class TestHabit {

    private Habit mockHabit() {

        Date startDate = new Date();
        Date endDate = new Date();
        List<MaterialDayPicker.Weekday> weekdayList;
        MaterialDayPicker dayPicker = new MaterialDayPicker(ApplicationProvider.getApplicationContext());
        dayPicker.selectDay(MaterialDayPicker.Weekday.FRIDAY);
        weekdayList = dayPicker.getSelectedDays();



        return new Habit("Reading", startDate, endDate, weekdayList, "new habit" );

    }

    @Test
    void testGetHabitName(){
        Habit habit = mockHabit();
        habit.setHabitName("new habit");
        assertTrue (habit.getHabitName().contains("new habit"));
    }

    @Test
    void testGetWeekdayList(){
        Habit habit = mockHabit();
        assertEquals(1, habit.getWeekdayList().size());
    }

    @Test
    void testGetStartDate(){
        Habit habit = mockHabit();
        //assertEquals(0, habit.getStart);
    }

}
