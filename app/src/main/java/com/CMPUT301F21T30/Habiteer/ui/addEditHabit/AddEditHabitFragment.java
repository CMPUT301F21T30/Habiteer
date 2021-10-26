package com.CMPUT301F21T30.Habiteer.ui.addEditHabit;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.R;

public class AddEditHabitFragment extends Fragment {

    private AddEditHabitModel mViewModel;

    public static AddEditHabitFragment newInstance() {
        return new AddEditHabitFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addedithabit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddEditHabitModel.class);
        // TODO: Use the ViewModel
    }

}