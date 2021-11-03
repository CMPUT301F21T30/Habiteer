package com.CMPUT301F21T30.Habiteer.ui.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.ui.habit.HabitAdapter;

import java.util.List;

public class ViewOthersHabitsAdapter extends RecyclerView.Adapter<ViewOthersHabitsAdapter.ViewHolder> {

    String dataHabitTitle[];
    Integer dataHabitProgress[];


    Context context;

    public  ViewOthersHabitsAdapter(Context ct, List<String> s1, List<Integer> s2){

        context = ct;
        dataHabitTitle = s1.toArray(new String[0]);
        dataHabitProgress = s2.toArray(new Integer[0]);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_view_others_habit, parent, false);
        return new ViewOthersHabitsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.habitTitle.setText(dataHabitTitle[position]);
        holder.userProgress.setPercentage(dataHabitProgress[position]);
        holder.userProgress.setStepCountText(String.valueOf(dataHabitProgress[position]));
    }

    @Override
    public int getItemCount() {
        return dataHabitTitle.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView habitTitle;
        com.app.progresviews.ProgressWheel userProgress;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            habitTitle = itemView.findViewById(R.id.habit_title_text);
            userProgress = itemView.findViewById(R.id.wheelProgress_habits);
        }
    }
}
