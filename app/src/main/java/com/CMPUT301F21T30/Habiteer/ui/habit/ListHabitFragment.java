package com.CMPUT301F21T30.Habiteer.ui.habit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ListHabitFragment extends Fragment implements TabLayout.OnTabSelectedListener{

    private ListHabitViewModel listHabitViewModel;
    private FragmentListhabitBinding binding;
    private ArrayList<Habit> habitList;
    private HabitAdapter habitAdapter;
    private HabitAdapter todayHabitAdapter;
    private RecyclerView habitRecycler;
    private TabLayout tabLayout;

    /**
     * This method creates the list habits view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root, which is the root View
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the binding first
        binding = FragmentListhabitBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listHabitViewModel = new ViewModelProvider(this).get(ListHabitViewModel.class);
        habitList = new ArrayList<>();
        habitRecycler = root.findViewById(R.id.habit_recycler);
        listHabitViewModel.getHabits().observe(getViewLifecycleOwner(), new Observer<List<Habit>>() {
            @Override
            public void onChanged(@Nullable List<Habit> habits) {
                todayHabitAdapter.notifyDataSetChanged();
                habitAdapter.notifyDataSetChanged();
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

        /* Create tabs */
        tabLayout = (TabLayout)root.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_1_text)));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_2_text));
        tabLayout.addOnTabSelectedListener(this);

        return root;
    }
    private void recyclerSetup() {
        habitAdapter = new HabitAdapter(listHabitViewModel.getHabits().getValue());
//        todayHabitAdapter = new HabitAdapter(listHabitViewModel.getTodayHabits().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        habitRecycler.setLayoutManager(layoutManager);
        habitRecycler.setItemAnimator(new DefaultItemAnimator());
        habitRecycler.setAdapter(habitAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(habitRecycler.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        habitRecycler.addItemDecoration(divider);
    }

    /**
     *
     */
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

    /**
     * This method changes what habits are displayed based on the tab selected
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getText() == getString(R.string.tab_2_text)) { // if tab is "Today"
            System.out.println("ALl habits tab selected");
            System.out.println(Session.getInstance().getHabitList());
            habitRecycler.setAdapter(todayHabitAdapter);
            todayHabitAdapter.notifyDataSetChanged();
        }
        else {
            System.out.println("Today habits tab selected");
            System.out.println(Session.getInstance().getHabitList());
            habitRecycler.setAdapter(habitAdapter);
            habitAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}