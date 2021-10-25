package com.CMPUT301F21T30.Habiteer.ui.habit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListHabitViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListHabitViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}