package com.CMPUT301F21T30.Habiteer.ui.Follow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.ArrayList;

public class FollowUserHabitAdapter extends RecyclerView.Adapter {
    private ArrayList<Habit> habitsList;
    public FollowUserHabitAdapter(ArrayList<Habit> habitsList) {
        this.habitsList = habitsList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_user_habit_content, parent, false);
        return new FollowUserHabitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //holder.followUserHabit.setText(habitsList.get(position).getHabitName());
        //holder.followUserProgress.setProgress(habitsList.get(position).getProgress());
        //holder.progressPercentage.setText(habitsList.get(position).getProgress.toString());


    }

    @Override
    public int getItemCount() {
        return habitsList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView followUserHabit;
        ProgressBar followUserProgress;
        TextView progressPercentage;
        public ViewHolder(View view) {
            super(view);

            followUserHabit = view.findViewById(R.id.followUserHabit);
            followUserProgress = view.findViewById(R.id.followUserProgress);
            progressPercentage = view.findViewById(R.id.progressPercentage);

        }
    }
}
