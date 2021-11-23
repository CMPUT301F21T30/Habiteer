/**
 * Resources: https://developer.android.com/guide/topics/ui/layout/recyclerview
 *
 */
package com.CMPUT301F21T30.Habiteer.ui.habit;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Locale;

import ca.antonious.materialdaypicker.MaterialDayPicker;

/**
 * Custom adapter for habitList.
 * Allows for updates to the list as Habits are added, edited, or deleted
 */
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
        /**
         * This method updates the Habit List and stores it in Firestore
         */
        Session session = Session.getInstance();
        this.habitArrayList = session.getHabitList();
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
        List<MaterialDayPicker.Weekday> habitDays_raw = habitArrayList.get(position).getWeekdayList(); // raw list of days
        String daysString = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // Requires Java 8
            daysString = formatDayList(habitDays_raw); // format the list to a string
        }
        else {
            daysString = habitDays_raw.toString(); // just in case, old java versions with get this ugly default string
        }
        holder.habitRepeats.setText(daysString);


    }

    /**
     * Converts a list of days into formatted string form
     * @param habitDays_raw, a list of days from MaterialDatePicker
     * @return daysString, a comma separated list of the abbreviated days in string form (e.g. "Mon, Tue, Wed"
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String formatDayList(List<MaterialDayPicker.Weekday> habitDays_raw){
        StringBuilder daysStringBuilder = new StringBuilder();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive() // case insensitive day string
                .appendPattern("EEEE") // pattern for day of the week
                .toFormatter(Locale.getDefault());
        for (int i = 0; i < habitDays_raw.size(); i++) {
            MaterialDayPicker.Weekday day = habitDays_raw.get(i); // loop through days

            TemporalAccessor accessor = formatter.parse(day.toString()); // parse the day of week
            DayOfWeek dayOfWeek = DayOfWeek.from(accessor);  // convert into a java.DayOfWeek object
            // get the abbreviation and add it to the string
            daysStringBuilder.append(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
            if (i!=habitDays_raw.size()-1) {
                // only append comma and space if not last element
                daysStringBuilder.append(", ");
            }
        }
        return daysStringBuilder.toString(); // return the string list of days
    }
    @Override
    public int getItemCount() {
        try {
            return habitArrayList.toArray().length;
        }
        catch (NullPointerException e) {
            System.out.println("habitAdapter is empty: " + e);
            return 0;
        }
    }

}
