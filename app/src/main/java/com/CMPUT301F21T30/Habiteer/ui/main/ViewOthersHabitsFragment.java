package com.CMPUT301F21T30.Habiteer.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CMPUT301F21T30.Habiteer.R;

public class ViewOthersHabitsFragment extends Fragment {

    private OthersHabitsListViewModel mViewModel;

    public static ViewOthersHabitsFragment newInstance() {
        return new ViewOthersHabitsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.others_habits_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OthersHabitsListViewModel.class);
        // TODO: Use the ViewModel
    }

}