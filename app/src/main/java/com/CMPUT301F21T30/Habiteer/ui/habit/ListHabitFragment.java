package com.CMPUT301F21T30.Habiteer.ui.habit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.databinding.FragmentListhabitBinding;
import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class ListHabitFragment extends Fragment {

    private ListHabitViewModel listHabitViewModel;
    private FragmentListhabitBinding binding;
//    private ArrayList<Habit> habitList;
    private HabitAdapter habitAdapter;
    private RecyclerView habitRecycler;
    private Date start;
    private Date end;
    private List<MaterialDayPicker.Weekday> weekdayList;

    ArrayList<Habit> habitList;

    private HashMap<String,Habit> habitHashMap;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the binding first
        binding = FragmentListhabitBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listHabitViewModel = new ViewModelProvider(this).get(ListHabitViewModel.class);
//        habitList = new ArrayList<>();

        habitHashMap = Session.getInstance().getHabitHashMap();
        habitList = new ArrayList<Habit>(habitHashMap.values());


        //start = Session.getInstance().getHabitHashMap().get()
        habitRecycler = root.findViewById(R.id.habit_recycler);
        listHabitViewModel.getHabits().observe(getViewLifecycleOwner(), new Observer<HashMap<String,Habit>>() {

            @Override
            public void onChanged(@Nullable HashMap<String,Habit> habits) {
                habitAdapter.notifyDataSetChanged();
                Session session = Session.getInstance();
                System.out.println("ListHabitFragment Session: " + session);
                System.out.println("ListHabitFragment user: " + session.getUser());
            }
        });
        recyclerSetup();

        FloatingActionButton fab = root.findViewById(R.id.FAB_addHabit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getContext(), AddEditHabitActivity.class);
               intent.putExtra("EditMode",false);
               startActivity(intent);
            }
        });

        return root;

    }
    private void recyclerSetup() {
        habitAdapter = new HabitAdapter(listHabitViewModel.getHabits().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        habitRecycler.setLayoutManager(layoutManager);
        habitRecycler.setItemAnimator(new DefaultItemAnimator());
        habitRecycler.setAdapter(habitAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(habitRecycler.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        habitRecycler.addItemDecoration(divider);
    }



    @Override
    public void onResume() {
        // when the fragment resumes (navigated to)

//        Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
        habitAdapter.updateDataFromSession();
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}