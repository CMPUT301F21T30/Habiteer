package com.CMPUT301F21T30.Habiteer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;

import java.util.List;

public class ViewOtherHabitsAdapter extends RecyclerView.Adapter<ViewOtherHabitsAdapter.ViewHolder>{

    private List<Habit> habitArrayList;

    public  ViewOtherHabitsAdapter(List<Habit> habitArrayList){

        this.habitArrayList = habitArrayList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_other_habits_content, parent, false);
        return new ViewOtherHabitsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewOtherHabitsAdapter.ViewHolder holder, int position) {
        holder.habitTitle.setText(habitArrayList.get(position).getHabitName());
        holder.progressIndicator.setProgress(habitArrayList.get(position).getProgress());
        holder.progressPercentage.setText(habitArrayList.get(position).getProgress().toString());

    }

    @Override
    public int getItemCount() {
        return habitArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView habitTitle;
        ProgressBar progressIndicator;
        TextView progressPercentage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            habitTitle = itemView.findViewById(R.id.habit_title_text);
            progressIndicator = itemView.findViewById(R.id.circular_progress);
            progressPercentage = itemView.findViewById(R.id.progress_percentage);
        }
    }
}
