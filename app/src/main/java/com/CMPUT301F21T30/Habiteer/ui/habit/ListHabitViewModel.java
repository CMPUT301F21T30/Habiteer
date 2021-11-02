/**
 * ViewModel for listing habits
 * Handles habit RecyclerView
 * Resources: https://developer.android.com/reference/android/arch/lifecycle/MutableLiveData
 *            https://stackoverflow.com/questions/47941537/notify-observer-when-item-is-added-to-list-of-livedata
 *            https://codingwithmitch.com/blog/getting-started-with-mvvm-android/
 */

package com.CMPUT301F21T30.Habiteer.ui.habit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListHabitViewModel extends ViewModel {

    /* Habit list data */
    private MutableLiveData<List<Habit>> mHabits = new MutableLiveData<>();
    private ArrayList<Habit> habitData;

    /* Tab data */
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();


    public LiveData<List<Habit>> getHabits() {
        habitData = new ArrayList<>();
        habitData.add(new Habit("Habit 1")); // TODO remove example data
        habitData.add(new Habit("Habit 2")); // TODO remove example data
        mHabits.setValue(habitData);
        return mHabits;
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }
}