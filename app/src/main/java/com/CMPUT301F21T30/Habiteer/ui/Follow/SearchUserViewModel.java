package com.CMPUT301F21T30.Habiteer.ui.Follow;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class SearchUserViewModel extends ViewModel {
    private ArrayList<User> searchList;

    public void setSearchList(){
        this.searchList = searchList;

    }
    public ArrayList<User> getSearchList(){
        return searchList;

    }
}