/**
 * ViewModel for listing habits
 * Handles habit RecyclerView
 * Resources: https://developer.android.com/reference/android/arch/lifecycle/MutableLiveData
 *            https://stackoverflow.com/questions/47941537/notify-observer-when-item-is-added-to-list-of-livedata
 *            https://codingwithmitch.com/blog/getting-started-with-mvvm-android/
 */

package com.CMPUT301F21T30.Habiteer.ui.habit;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.LONG;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.CMPUT301F21T30.Habiteer.Session;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ca.antonious.materialdaypicker.MaterialDayPicker;

/**
 * This class handles data sent to the HabitAdapter
 */
public class ListHabitViewModel extends ViewModel {

    /* Habit list data */
    private MutableLiveData<List<Habit>> mHabits = new MutableLiveData<>();
    private MutableLiveData<List<Habit>> mTodayHabits = new MutableLiveData<>();
    private ArrayList<Habit> habitList;
    private ArrayList<Habit> todayHabitList;

    /* Tab data */
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    /**
     * This method gets all habits belonging to the signed in user
     * @return Live data list of all habits
     */
    public LiveData<List<Habit>> getHabits() {
        habitList = Session.getInstance().getHabitList();
        mHabits.setValue(habitList);
        System.out.println(mHabits);
        return mHabits;
    }

    /**
     * This method gets all of today's habits belonging to the signed in user
     * @return Live data list of today's habits
     */
    public LiveData<List<Habit>> getTodayHabits() {
        todayHabitList = Session.getInstance().getHabitList();
        String today = getDayOfWk();
        /* remove habits that do not contain today */
        for (int i = 0; i < todayHabitList.size(); i++) {
            Habit habit = todayHabitList.get(i);
            System.out.println(MaterialDayPicker.Weekday.valueOf(today));
            if (!habit.getWeekdayList().contains(MaterialDayPicker.Weekday.valueOf(today))) {
                todayHabitList.remove(habit);
                i--;
            }
        }
        mTodayHabits.setValue(todayHabitList);
        return mTodayHabits;
    }

    private String getDayOfWk() {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(DAY_OF_WEEK);
        calendar.set(DAY_OF_WEEK,today);
        return calendar.getDisplayName(DAY_OF_WEEK, LONG, Locale.US).toUpperCase(Locale.ROOT);
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }
}