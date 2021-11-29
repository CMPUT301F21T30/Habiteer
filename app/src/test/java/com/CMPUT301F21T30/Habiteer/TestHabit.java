package com.CMPUT301F21T30.Habiteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;


/**
 * tests habit class
 * tests getter and setters for habit class
 */

public class TestHabit {

    //creates a mock habit
    private Habit mockHabit() {

        Date startDate = new Date();
        Date endDate = new Date();
        List<MaterialDayPicker.Weekday> weekdayList = new ArrayList<>();

        weekdayList.add(MaterialDayPicker.Weekday.FRIDAY);

        return new Habit("Reading", startDate, endDate, weekdayList, "new habit" );

    }

    //test getter and setter for habit name attribute
    @Test
    void testGetHabitName(){
        Habit habit = mockHabit();
        habit.setHabitName("new habit");
        assertTrue (habit.getHabitName().contains("new habit"));
    }

    //test getter and setter for habit weekday list
    @Test
    void testGetWeekdayList(){
        Habit habit = mockHabit();
        List<MaterialDayPicker.Weekday> weekdayList2 = new ArrayList<>();

        weekdayList2.add(MaterialDayPicker.Weekday.SUNDAY);
        habit.setWeekdayList(weekdayList2);

        assertEquals(1, habit.getWeekdayList().size());
    }

    //test getter and setter for habit start date attribute
    @Test
    void testGetStartDate(){
        Habit habit = mockHabit();
        Date startDate = new Date();
        habit.setStartDate(startDate);
        assertEquals(startDate, habit.getStartDate());
    }

    //test getter and setter for habit end date attribute
    @Test
    void testGetEndDate(){
        Habit habit = mockHabit();
        Date endDate = new Date();
        habit.setEndDate(endDate);
        assertEquals(endDate, habit.getEndDate());
    }

    //test getter and setter for habit reason attribute
    @Test
    void testGetReason(){
        Habit habit = mockHabit();

        String reason = "new habit";
        habit.setReason(reason);
        assertEquals("new habit", habit.getReason());

    }

    //test getter and setter for habit Id attribute
    @Test
    void testGetId(){
        Habit habit = mockHabit();

        String habitID = "900mn";
        habit.setId(habitID);
        assertEquals(habitID, habit.getId());

    }


}
