package com.CMPUT301F21T30.Habiteer.ui.habit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.databinding.FragmentListhabitBinding;

import java.util.ArrayList;
import java.util.List;

public class ListHabitFragment extends Fragment {

    private ListHabitViewModel listHabitViewModel;
    private FragmentListhabitBinding binding;
    private ArrayList<Habit> habitList;
    private HabitAdapter habitAdapter;
    private RecyclerView habitRecycler;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the binding first
        binding = FragmentListhabitBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listHabitViewModel = new ViewModelProvider(this).get(ListHabitViewModel.class);
        habitList = new ArrayList<>();
        habitRecycler = root.findViewById(R.id.habit_recycler);


//        final RecyclerView habitList = binding.habitRecycler;
        listHabitViewModel.getHabits().observe(getViewLifecycleOwner(), new Observer<List<Habit>>() {
            @Override
            public void onChanged(@Nullable List<Habit> habits) {
                habitAdapter.notifyDataSetChanged();
            }
        });
        recyclerSetup();
        return root;
    }
    private void recyclerSetup() {
        habitAdapter = new HabitAdapter(habitList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        habitRecycler.setLayoutManager(layoutManager);
        habitRecycler.setItemAnimator(new DefaultItemAnimator());
        habitRecycler.setAdapter(habitAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}