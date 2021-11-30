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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.databinding.FragmentListhabitBinding;
import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.LinkedHashMap;

public class ListHabitFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private ListHabitViewModel listHabitViewModel;
    private FragmentListhabitBinding binding;
    private HabitAdapter habitAdapter;
    private HabitAdapter todayHabitAdapter;
    private RecyclerView habitRecycler;
    private TabLayout tabLayout;

    /**
     * This method creates the list habits view
     * 
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
        habitRecycler = root.findViewById(R.id.habit_recycler);
        listHabitViewModel.getHabits().observe(getViewLifecycleOwner(), new Observer<LinkedHashMap<String, Habit>>() {
            @Override
            public void onChanged(@Nullable LinkedHashMap<String, Habit> habits) {
                habitAdapter.notifyDataSetChanged();
                todayHabitAdapter.notifyDataSetChanged();
            }
        });
        // long press and drag to reorder habits
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(habitRecycler);
        // Set up recycler view
        recyclerSetup();

        FloatingActionButton fab = root.findViewById(R.id.FAB_addHabit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddEditHabitActivity.class);
                intent.putExtra("EditMode", false);
                startActivity(intent);
            }
        });

        /* Create tabs */
        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_1_text)));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_2_text));
        tabLayout.addOnTabSelectedListener(this);

        return root;
    }

    private void recyclerSetup() {
        habitAdapter = new HabitAdapter(listHabitViewModel.getHabits().getValue(), Session.getInstance().getUser().getHabitIdList());
        todayHabitAdapter = new HabitAdapter(listHabitViewModel.getTodayHabits().getValue(), listHabitViewModel.getTodayHabitIdList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        habitRecycler.setLayoutManager(layoutManager);
        habitRecycler.setItemAnimator(new DefaultItemAnimator());
        habitRecycler.setAdapter(habitAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(habitRecycler.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        habitRecycler.addItemDecoration(divider);

    }

    /**
     * Updates hashmaps and notifies adapters of change when "My Habits" page is navigated to
     */
    @Override
    public void onResume() {
        // when the fragment resumes (navigated to)
        habitAdapter.updateDataFromSession();
        todayHabitAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * This method changes what habits are displayed based on the tab selected
     * 
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getText() == getString(R.string.tab_2_text)) { // if tab is "Today"
            /* Display today's habits */
            todayHabitAdapter = new HabitAdapter(listHabitViewModel.getTodayHabits().getValue(), listHabitViewModel.getTodayHabitIdList());
            habitRecycler.setAdapter(todayHabitAdapter);
            todayHabitAdapter.notifyDataSetChanged();
        } else { // tab is "All Habits"
            /* Display all habits */
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

    /* Item touch helper allows habits to be dragged and reordered */
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int initialPos = viewHolder.getAbsoluteAdapterPosition();
            int newPos = target.getAbsoluteAdapterPosition();

            Session.getInstance().swapHabitOrder(initialPos,newPos);

            recyclerView.getAdapter().notifyItemMoved(initialPos, newPos);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}