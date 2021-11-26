package com.CMPUT301F21T30.Habiteer.ui.Follow;

import androidx.lifecycle.ViewModel;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class SearchUserViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private ArrayList<User> searchList;
}