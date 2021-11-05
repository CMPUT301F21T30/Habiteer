/**
 * Resources: https://developer.android.com/guide/topics/ui/layout/recyclerview
 *
 */
package com.CMPUT301F21T30.Habiteer.ui.habit;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {
    private List<Habit> habitArrayList;
    private int selectedIndex = RecyclerView.NO_POSITION;

    public HabitAdapter(List<Habit> habitArrayList) {
        this.habitArrayList = habitArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView habitNameText;
        private TextView habitEndDate;
        private TextView habitRepeats;

        public ViewHolder(final View view) {
            super(view);
            habitNameText = view.findViewById(R.id.habit_name);
            habitRepeats = view.findViewById(R.id.repeats);
            habitEndDate = view.findViewById(R.id.end_date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(selectedIndex);
            selectedIndex = getLayoutPosition();
            System.out.println(selectedIndex);
            notifyItemChanged(selectedIndex);

            // Create new intent to start view habit activity
            Intent intent = new Intent(view.getContext(),ViewHabitActivity.class);
            intent.putExtra("habitIndex",selectedIndex); // pass through the index of the clicked item
            view.getContext().startActivity(intent); // start the view habit activity
        }
    }
    public void updateDataFromSession() {
        this.habitArrayList = Session.getInstance().getUser().getHabitList() ;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HabitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitAdapter.ViewHolder holder, int position) {
        // Set data in habit list
        holder.itemView.setSelected(selectedIndex == position);
        String habitName = habitArrayList.get(position).getHabitName();
        SimpleDateFormat dateFormatter =  new SimpleDateFormat("MMM dd, yyyy");
        String habitDate = dateFormatter.format(habitArrayList.get(position).getEndDate());
        holder.habitNameText.setText(habitName);
        holder.habitEndDate.setText(habitDate);
//        holder.habitRepeats.setText(); //TODO set repeat


    }

    @Override
    public int getItemCount() {
        return habitArrayList.toArray().length;
    }

}
