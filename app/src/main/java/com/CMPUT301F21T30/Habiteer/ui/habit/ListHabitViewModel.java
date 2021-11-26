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

import com.CMPUT301F21T30.Habiteer.Session;

import java.util.HashMap;

/**
 * This class handles data sent to the HabitAdapter
 */
public class ListHabitViewModel extends ViewModel {

    /* Habit list data */
    private MutableLiveData<HashMap<String,Habit>> mHabits = new MutableLiveData<>();
    private HashMap<String,Habit> habitList;

    /* Tab data */
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();


    public LiveData<HashMap<String,Habit>> getHabits() {
        habitList = Session.getInstance().getHabitHashMap();
        mHabits.setValue(habitList);
        return mHabits;
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }
}