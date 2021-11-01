/**
 * Resources: https://developer.android.com/guide/topics/ui/layout/recyclerview
 *
 */
package com.CMPUT301F21T30.Habiteer.ui.habit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {
    private List<Habit> habitArrayList;
    private int selectedIndex = RecyclerView.NO_POSITION;

    public HabitAdapter(List<Habit> habitArrayList) {
        this.habitArrayList = habitArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView habitNameText;

        public ViewHolder(final View view) {
            super(view);
            habitNameText = view.findViewById(R.id.habit_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(selectedIndex);
            selectedIndex = getLayoutPosition();
            System.out.println(selectedIndex);
            notifyItemChanged(selectedIndex);
        }
    }

    @NonNull
    @Override
    public HabitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitAdapter.ViewHolder holder, int position) {
        holder.itemView.setSelected(selectedIndex == position);
        String habitName = habitArrayList.get(position).getHabitName();
        holder.habitNameText.setText(habitName);

    }

    @Override
    public int getItemCount() {

        return habitArrayList.toArray().length;
    }

}
