package com.CMPUT301F21T30.Habiteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class TestHabit {
    private Habit mockHabit() {

        Date startDate = new Date();
        Date endDate = new Date();
        List<MaterialDayPicker.Weekday> weekdayList = new List<MaterialDayPicker.Weekday>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<MaterialDayPicker.Weekday> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(MaterialDayPicker.Weekday weekday) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends MaterialDayPicker.Weekday> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends MaterialDayPicker.Weekday> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public MaterialDayPicker.Weekday get(int i) {
                return null;
            }

            @Override
            public MaterialDayPicker.Weekday set(int i, MaterialDayPicker.Weekday weekday) {
                return null;
            }

            @Override
            public void add(int i, MaterialDayPicker.Weekday weekday) {

            }

            @Override
            public MaterialDayPicker.Weekday remove(int i) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<MaterialDayPicker.Weekday> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<MaterialDayPicker.Weekday> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<MaterialDayPicker.Weekday> subList(int i, int i1) {
                return null;
            }
        };
        weekdayList.add(MaterialDayPicker.Weekday.FRIDAY);


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
        assertEquals(0, habit.getWeekdayList().size());
    }

    @Test
    void testGetStartDate(){
        Habit habit = mockHabit();
        //assertEquals(0, habit.getStart);
    }

}
